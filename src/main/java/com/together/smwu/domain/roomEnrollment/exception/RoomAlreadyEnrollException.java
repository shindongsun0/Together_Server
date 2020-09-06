package com.together.smwu.domain.roomEnrollment.exception;

import com.together.smwu.global.advice.exception.BusinessException;
import com.together.smwu.global.advice.exception.ErrorCode;

public class RoomAlreadyEnrollException extends BusinessException {

    public RoomAlreadyEnrollException() {
        super(ErrorCode.ROOM_ALREADY_EXISTS);
    }
}
