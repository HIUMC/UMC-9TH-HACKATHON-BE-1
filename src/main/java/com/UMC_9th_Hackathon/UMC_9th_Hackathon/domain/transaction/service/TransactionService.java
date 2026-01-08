package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.service;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.TransactionReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.entity.Transaction;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    private final TransactionRepository transactionRepository;
//    private final CategoryRepository categoryRepository;

    @Transactional
    public Long saveTransaction(TransactionReqDTO.TransactionCreateRequest request) {
//        Category category = categoryRepository.findById(request.categoryId())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Transaction transaction = Transaction.builder()
                .type(request.type())
                .price(request.price())
                .transactionDate(request.date())
                .memo(request.memo())
                .photoUrl(request.photoUrl()) // 프론트가 보낸 S3 주소를 그대로 저장
                // .category(category)
                .build();

        transactionRepository.save(transaction);

        return transaction.getId();
    }

    @Transactional
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
