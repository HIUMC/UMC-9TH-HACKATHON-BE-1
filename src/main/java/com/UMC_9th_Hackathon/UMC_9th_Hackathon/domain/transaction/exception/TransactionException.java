package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.exception.GeneralException;

public class TransactionException extends GeneralException {
    public TransactionException(BaseErrorCode code) {
        super(code);
    }
}
