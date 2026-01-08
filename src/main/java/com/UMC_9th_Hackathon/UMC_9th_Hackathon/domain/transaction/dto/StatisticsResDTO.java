package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto;

import java.util.List;

public class StatisticsResDTO {

    public record MonthlyStats(
            String month,
            IncomeStats income,
            ExpenseStats expense,
            Long remainingBudget,    // 총 수입 - 총 지출
            String remainingStatus  // POSITIVE | NEGATIVE | ZERO
    ) {}

    public record IncomeStats(
            Long total,
            List<CategoryAmount> byCategory
    ) {}

    public record ExpenseStats(
            Long total,
            List<CategoryRatio> byCategory,
            CategoryAmount maxCategory
    ) {}

    public record CategoryAmount(
            Long categoryId,
            String categoryName,
            Long amount
    ) {}

    public record CategoryRatio(
            Long categoryId,
            String categoryName,
            Long amount,
            Double ratio
    ) {}
}
