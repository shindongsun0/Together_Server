package com.together.smwu.domain.place.exception;

import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class PlaceNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "존재하지 않는 장소입니다.";
    private static final String TARGET_MESSAGE = "장소를 찾을 수 없습니다.";

    public PlaceNotFoundException() {
        super(MESSAGE);
    }

    public PlaceNotFoundException(String target) {
        super(target + TARGET_MESSAGE);
    }
}
