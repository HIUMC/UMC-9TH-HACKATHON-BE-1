package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception.code;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransactionErrorCode implements BaseErrorCode {

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "TRANSACTION404_1", "해당 카테고리를 찾을 수 없습니다."),
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "TRANSACTION404_2", "해당 내역을 찾을 수 없습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSACTION500_1", "예기치 않은 서버 에러가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "TRANSACTION404_3", "해당 카테고리를 찾을 수 없습니다."),
    ;



    private final HttpStatus status;
    private final String code;
    private final String message;
}
