package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.exception.code;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode implements BaseErrorCode {
    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "CATEGORY400_1", "이미 존재하는 카테고리입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY404_1", "카테고리가 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
