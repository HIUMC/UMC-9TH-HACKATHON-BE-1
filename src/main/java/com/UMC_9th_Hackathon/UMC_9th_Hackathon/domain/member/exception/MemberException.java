package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.exception.GeneralException;

public class MemberException extends GeneralException {
  public MemberException(BaseErrorCode code) {
    super(code);
  }
}
