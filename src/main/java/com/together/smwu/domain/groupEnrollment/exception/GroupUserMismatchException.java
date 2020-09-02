package com.together.smwu.domain.groupEnrollment.exception;

import com.together.smwu.global.exception.ExpectedException;

public class GroupUserMismatchException extends ExpectedException {

    public static final String GROUP_USER_MISMATCH_MESSAGE = "회원님은 그룹에 속해있지 않습니다.";

   public GroupUserMismatchException() {
        super(GROUP_USER_MISMATCH_MESSAGE);
    }
}