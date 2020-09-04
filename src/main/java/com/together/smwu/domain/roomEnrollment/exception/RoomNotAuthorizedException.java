package com.together.smwu.domain.roomEnrollment.exception;

import com.together.smwu.global.advice.exception.BusinessException;
import com.together.smwu.global.advice.exception.ErrorCode;

public class RoomNotAuthorizedException extends BusinessException {

    public RoomNotAuthorizedException() {
        super(ErrorCode.ROOM_NOT_AUTHORIZED);
    }
}
