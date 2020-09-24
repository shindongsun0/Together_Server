package com.together.smwu.domain.place.exception;

import com.together.smwu.global.advice.exception.BusinessException;
import com.together.smwu.global.advice.exception.ErrorCode;

public class PlaceAlreadyEnrolledException extends BusinessException {
    public PlaceAlreadyEnrolledException() {
        super(ErrorCode.PLACE_ALREADY_EXISTS);
    }
}
