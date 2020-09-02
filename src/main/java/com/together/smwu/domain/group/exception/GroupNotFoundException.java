package com.together.smwu.domain.group.exception;


import com.together.smwu.global.exception.ExpectedException;

public class GroupNotFoundException extends ExpectedException {

    private static final String MESSAGE = "존재하지 않는 그룹입니다.";

    public GroupNotFoundException() {
        super(MESSAGE);
    }
}
