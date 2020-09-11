package com.together.smwu.domain.roomEnrollment.exception;

import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class RoomUserMismatchException extends EntityNotFoundException {

    public static final String ROOM_USER_MISMATCH_MESSAGE = "회원님은 방에 속해있지 않습니다.";

   public RoomUserMismatchException() {
        super(ROOM_USER_MISMATCH_MESSAGE);
    }
}
