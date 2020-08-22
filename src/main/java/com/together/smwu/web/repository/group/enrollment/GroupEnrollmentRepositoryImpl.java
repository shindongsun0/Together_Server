package com.together.smwu.web.repository.group.enrollment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.together.smwu.web.repository.group.enrollment.QGroupEnrollment.groupEnrollment;

@RequiredArgsConstructor
public class GroupEnrollmentRepositoryImpl implements GroupEnrollmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long deleteAllByGroupId(long groupId) {
        return queryFactory
                .delete(groupEnrollment)
                .where(groupEnrollment.group.id.eq(groupId))
                .execute();
    }

    @Override
    public long deleteAllByUserId(long userId) {
        return queryFactory
                .delete(groupEnrollment)
                .where(groupEnrollment.user.msrl.eq(userId))
                .execute();
    }

    @Override
    public GroupEnrollment findByUserId(long userId) {
        return queryFactory
                .select(groupEnrollment)
                .from(groupEnrollment)
                .where(groupEnrollment.user.msrl.eq(userId))
                .fetchOne();
    }
}
