package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.service;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.repository.CategoryRepository;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception.MemberErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception.MemberException;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.repository.MemberRepository;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.StatisticsResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.TransactionReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.TransactionResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.entity.Transaction;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.enums.TransactionType;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception.TransactionNotFoundException;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveTransaction(TransactionReqDTO.TransactionCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        Transaction transaction = Transaction.builder()
                .type(request.type())
                .price(request.price())
                .transactionDate(request.date())
                .memo(request.memo())
                .photoUrl(request.photoUrl())
                .member(member) // 내역에 멤버 정보 연결
                .build();

        return transactionRepository.save(transaction).getId();
    }

    @Transactional
    public void deleteTransaction(Long transactionId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        // 2. 해당 멤버의 내역이 맞는지 확인하며 조회
        Transaction transaction = transactionRepository.findByIdAndMember(transactionId, member)
                .orElseThrow(TransactionNotFoundException::new);

        // 3. 삭제 실행
        transactionRepository.delete(transaction);
    }

    @Transactional
    public void deleteAllTransactions(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));
        transactionRepository.deleteAllByMember(member);
    }

    public TransactionResDTO.DailyTransactionResponse getDailyTransactions(LocalDate date, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        List<Transaction> transactions = transactionRepository.findAllByTransactionDateAndMember(date, member);

        // 아이템 리스트 변환
        List<TransactionResDTO.TransactionDetail> items = transactions.stream()
                .map(t -> TransactionResDTO.TransactionDetail.builder()
                        .transactionId(t.getId())
                        .type(t.getType())
                        .categoryName(t.getCategory() != null ? t.getCategory().getName() : "미지정") // 카테고리 연동 대비
                        .categoryIcon(t.getCategory() != null ? t.getCategory().getImgUrl() : null)
                        .memo(t.getMemo())
                        .price(t.getPrice())
                        .imageUrl(t.getPhotoUrl())
                        .build())
                .toList();

        // 수입/지출 합계 계산
        long income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToLong(Transaction::getPrice)
                .sum();

        long expense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToLong(Transaction::getPrice)
                .sum();

        return TransactionResDTO.DailyTransactionResponse.builder()
                .selectedDate(date)
                .items(items)
                .dailySummary(new TransactionResDTO.DailySummary(income, expense))
                .build();
    }

    public TransactionResDTO.MonthlySummaryResponse getMonthlySummary(int year, int month, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        //해당 월의 시작일과 종료일 계산
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        //해당 기간의 모든 내역 조회
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetweenAndMember(startDate, endDate,  member);

        // 날짜별로 그룹화하여 합계 계산
        Map<LocalDate, List<Transaction>> groupedByDate = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionDate));

        List<TransactionResDTO.DailySummaryItem> dailyData = groupedByDate.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<Transaction> dailyList = entry.getValue();

                    long income = dailyList.stream()
                            .filter(t -> t.getType() == TransactionType.INCOME)
                            .mapToLong(Transaction::getPrice).sum();

                    long expense = dailyList.stream()
                            .filter(t -> t.getType() == TransactionType.EXPENSE)
                            .mapToLong(Transaction::getPrice).sum();

                    return new TransactionResDTO.DailySummaryItem(date, income, expense);
                })
                .sorted(Comparator.comparing(TransactionResDTO.DailySummaryItem::date)) // 날짜순 정렬
                .toList();

        return TransactionResDTO.MonthlySummaryResponse.builder()
                .year(year)
                .month(month)
                .dailyData(dailyData)
                .build();
    }

    @Transactional(readOnly = true)
    public StatisticsResDTO.MonthlyStats getMonthlyStatistics(Long memberId, String monthStr) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 날짜 범위 계산
        YearMonth yearMonth = YearMonth.parse(monthStr);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();


        List<Transaction> transactions = transactionRepository.findAllByMemberAndDateRange(member, start, end);


        Map<Category, Long> incomeMap = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingLong(Transaction::getPrice)));

        long totalIncome = incomeMap.values().stream().mapToLong(Long::longValue).sum();

        List<StatisticsResDTO.CategoryAmount> incomeByCategory = incomeMap.entrySet().stream()
                .map(e -> new StatisticsResDTO.CategoryAmount(e.getKey().getId(), e.getKey().getName(), e.getValue()))
                .toList();


        Map<Category, Long> expenseMap = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingLong(Transaction::getPrice)));

        long totalExpense = expenseMap.values().stream().mapToLong(Long::longValue).sum();

        List<StatisticsResDTO.CategoryRatio> expenseByCategory = expenseMap.entrySet().stream()
                .map(e -> {
                    double ratio = totalExpense == 0 ? 0 : (double) e.getValue() / totalExpense * 100;
                    return new StatisticsResDTO.CategoryRatio(
                            e.getKey().getId(),
                            e.getKey().getName(),
                            e.getValue(),
                            Math.round(ratio * 10.0) / 10.0 // 소수점 첫째자리까지
                    );
                }).toList();


        StatisticsResDTO.CategoryAmount maxCategory = expenseMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> new StatisticsResDTO.CategoryAmount(e.getKey().getId(), e.getKey().getName(), e.getValue()))
                .orElse(null);


        long remainingBudget = totalIncome - totalExpense;
        String remainingStatus = remainingBudget > 0 ? "POSITIVE" : (remainingBudget < 0 ? "NEGATIVE" : "ZERO");

        return new StatisticsResDTO.MonthlyStats(
                monthStr,
                new StatisticsResDTO.IncomeStats(totalIncome, incomeByCategory),
                new StatisticsResDTO.ExpenseStats(totalExpense, expenseByCategory, maxCategory),
                remainingBudget,
                remainingStatus
        );
    }
}
