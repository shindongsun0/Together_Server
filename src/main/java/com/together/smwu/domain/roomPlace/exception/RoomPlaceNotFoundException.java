package com.together.smwu.domain.roomPlace.exception;

import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class RoomPlaceNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "방의 장소를 찾을 수 없습니다.";

    public RoomPlaceNotFoundException() {
        super(MESSAGE);
    }

    public RoomPlaceNotFoundException(String target) {
        super(target + MESSAGE);
    }
}

