package com.together.smwu.domain.group.exception;


import com.together.smwu.global.advice.exception.EntityNotFoundException;

public class GroupNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "존재하지 않는 그룹입니다.";
    private static final String TARGET_MESSAGE = "로 검색되는 그룹이 없습니다.";

    public GroupNotFoundException() {
        super(MESSAGE);
    }

    public GroupNotFoundException(String target){
        super(target + TARGET_MESSAGE);
    }
}
