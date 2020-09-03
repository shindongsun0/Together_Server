package com.together.smwu.domain.user.exception;

import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "존재하지 않는 사용자입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
