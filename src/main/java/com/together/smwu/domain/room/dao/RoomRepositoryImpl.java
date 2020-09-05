package com.together.smwu.domain.room.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.room.domain.Room;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.room.domain.QRoom.room;

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
}
