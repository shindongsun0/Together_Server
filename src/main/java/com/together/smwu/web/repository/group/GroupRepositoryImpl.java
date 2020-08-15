package com.together.smwu.web.repository.group;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.web.repository.group.QGroup.group;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

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
