package com.together.smwu.domain.groupEnrollment.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;
import lombok.RequiredArgsConstructor;

import static com.together.smwu.domain.groupEnrollment.domain.QGroupEnrollment.groupEnrollment;

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