package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.exception;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.exception.GeneralException;

public class CategoryException extends GeneralException {

    public CategoryException(BaseErrorCode code) {
        super(code);
    }
}
