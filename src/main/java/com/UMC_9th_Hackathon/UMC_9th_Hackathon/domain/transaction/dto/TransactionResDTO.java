package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.enums.TransactionType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class TransactionResDTO {

    // 날짜별 상세 내역 응답 (최종 result에 담길 객체)
    @Builder
    public record DailyTransactionResponse(
            LocalDate selectedDate,
            List<TransactionDetail> items,
            DailySummary dailySummary
    ) {}

    // 각 내역 아이템
    @Builder
    public record TransactionDetail(
            Long transactionId,
            TransactionType type,
            String categoryName,
            String categoryIcon,
            String memo,
            Long price,
            String imageUrl
    ) {}


    @Builder
    public record DailySummary(
            Long income,
            Long expense
    ) {}

    // 월별 요약 응답
    @Builder
    public record MonthlySummaryResponse(
            int year,
            int month,
            List<DailySummaryItem> dailyData
    ) {}

    // 날짜별 합계 아이템
    @Builder
    public record DailySummaryItem(
            LocalDate date,
            Long totalIncome,
            Long totalExpense
    ) {}
}
