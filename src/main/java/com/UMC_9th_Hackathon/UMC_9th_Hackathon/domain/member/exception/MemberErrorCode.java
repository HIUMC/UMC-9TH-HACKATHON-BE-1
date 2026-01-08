package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH401", ""); // 메시지 비움

    private final HttpStatus status;
    private final String code;
    private final String message;
}
