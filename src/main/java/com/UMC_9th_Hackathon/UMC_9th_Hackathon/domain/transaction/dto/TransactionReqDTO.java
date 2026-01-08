package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

public class TransactionReqDTO {

    // 내역 추가 Request
    @Builder
    public record TransactionCreateRequest(

            @NotNull(message = "추가할 내역은 수입 또는 지출 내역입니다.")
            TransactionType type,

            @NotNull(message = "금액은 필수입니다.")
            @Positive(message = "금액은 0보다 커야 합니다.")
            Long price,

            @NotNull(message = "카테코리 선택은 필수입니다.")
            Long categoryId,

            @NotNull(message = "날짜 입력은 필수입니다.")
            LocalDate date,

            @Size(max = 50, message = "메모는 50자 이내로 입력해주세요.")
            String memo,

            //S3 업로드 후 받은 이미지 URL
            String photoUrl
    ) {}
}
