package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception.code.TransactionErrorCode;

public class TransactionNotFoundException extends TransactionException {

    public TransactionNotFoundException() {
        super(TransactionErrorCode.TRANSACTION_NOT_FOUND);
    }
}