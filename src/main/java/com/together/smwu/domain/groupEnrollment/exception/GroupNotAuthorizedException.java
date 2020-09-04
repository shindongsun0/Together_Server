package com.together.smwu.domain.groupEnrollment.exception;

import com.together.smwu.global.advice.exception.BusinessException;
import com.together.smwu.global.advice.exception.ErrorCode;

public class GroupNotAuthorizedException extends BusinessException {

    public GroupNotAuthorizedException() {
        super(ErrorCode.GROUP_NOT_AUTHORIZED);
    }
}
