package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.exception.GeneralException;

public class LoginFailedException extends GeneralException {
    public LoginFailedException() {
        super(MemberErrorCode.LOGIN_FAILED);
    }
}