package com.together.smwu.domain.group.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.group.domain.Group;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.group.domain.QGroup.group;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements com.together.smwu.domain.group.dao.GroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Group> getAllGroups() {
        return queryFactory
                .selectFrom(group)
                .orderBy(group.createdTime.desc())
                .fetch();
    }
}
