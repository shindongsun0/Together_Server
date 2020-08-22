package com.together.smwu.web.repository.group.enrollment;

public interface GroupEnrollmentRepositoryCustom {

    long deleteAllByGroupId(long groupId);

    long deleteAllByUserId(long userId);

    GroupEnrollment findByUserId(long userId);
}
