package com.together.smwu.domain.groupEnrollment.dao;

import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;

public interface GroupEnrollmentRepositoryCustom {

    long deleteAllByGroupId(long groupId);

    long deleteAllByUserId(long userId);

    GroupEnrollment findByUserId(long userId);

    boolean checkIfMasterOfGroup(long groupId, long userId);
}
