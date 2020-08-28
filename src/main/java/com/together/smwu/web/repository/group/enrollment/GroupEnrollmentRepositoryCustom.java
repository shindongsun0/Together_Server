package com.together.smwu.web.repository.group.enrollment;

import com.together.smwu.web.domain.group.enrollment.GroupEnrollment;

public interface GroupEnrollmentRepositoryCustom {

    long deleteAllByGroupId(long groupId);

    long deleteAllByUserId(long userId);

    GroupEnrollment findByUserId(long userId);
}
