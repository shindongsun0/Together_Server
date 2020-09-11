package com.together.smwu.domain.room.exception;


import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class RoomNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "존재하지 않는 방입니다.";
    private static final String TARGET_MESSAGE = "방을 찾을 수 없습니다.";

    public RoomNotFoundException() {
        super(MESSAGE);
    }

    public RoomNotFoundException(String target) {
        super(target + TARGET_MESSAGE);
    }
}
