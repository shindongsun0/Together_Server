package com.together.smwu.domain.crawl.exception;

import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class KeyWordNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "존재하지 않는 키워드입니다.";
    private static final String TARGET_MESSAGE = "키워드를 찾을 수 없습니다.";

    public KeyWordNotFoundException() {
        super(MESSAGE);
    }

    public KeyWordNotFoundException(String target) {
        super(target + TARGET_MESSAGE);
    }
}
