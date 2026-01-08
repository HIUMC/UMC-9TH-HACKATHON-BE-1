package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception.code;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransactionSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK, "TRANSACTION200_1", "요청이 성공했습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "TRANSACTION202_1", "요청이 접수되었으나 처리가 완료되지 않았습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "TRANSACTION204_1", "요청이 성공했으나 반환할 콘텐츠가 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
