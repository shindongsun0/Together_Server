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

    @Override
    public long updateGroupImageUrl(long groupId, String imageUrl) {
        return queryFactory
                .update(group)
                .where(group.id.eq(groupId))
                .set(group.imageUrl, imageUrl)
                .execute();
    }

    @Override
    public long updateGroupContent(long groupId, String content) {
        return queryFactory
                .update(group)
                .where(group.id.eq(groupId))
                .set(group.content, content)
                .execute();
    }

    @Override
    public long updateGroupTitle(long groupId, String title) {
        return queryFactory
                .update(group)
                .where(group.id.eq(groupId))
                .set(group.title, title)
                .execute();
    }


}
