package com.together.smwu.domain.user.exception;

import com.together.smwu.global.exception.ExpectedException;

public class UserNotFoundException extends ExpectedException {

    private static final String MESSAGE = "존재하지 않는 사용자입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
