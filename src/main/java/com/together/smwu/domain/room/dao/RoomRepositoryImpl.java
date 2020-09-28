package com.together.smwu.domain.room.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.room.domain.Room;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.room.domain.QRoom.room;
import static com.together.smwu.domain.room.domain.QTag.tag;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> getAllRooms() {
        return queryFactory
                .selectFrom(room)
                .orderBy(room.createdTime.desc())
                .fetch();
    }

    @Override
    public List<Room> findByTagName(String tagName) {
        return queryFactory
                .selectFrom(room)
                .leftJoin(room.tags, tag).fetchJoin()
                .where(tag.name.contains(tagName))
                .fetch();
    }

    @Override
    public List<Room> findByTitle(String title) {
        return queryFactory
                .selectFrom(room)
                .where(room.title.contains(title))
                .fetch();
    }
}
