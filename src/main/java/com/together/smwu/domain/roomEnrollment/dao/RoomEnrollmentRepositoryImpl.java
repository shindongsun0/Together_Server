package com.together.smwu.domain.roomEnrollment.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.roomEnrollment.domain.QRoomEnrollment.roomEnrollment;

@RequiredArgsConstructor
public class RoomEnrollmentRepositoryImpl implements RoomEnrollmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long deleteAllByRoomId(long roomId) {
        return queryFactory
                .delete(roomEnrollment)
                .where(roomEnrollment.room.id.eq(roomId))
                .execute();
    }

    @Override
    public long deleteAllByUserId(long userId) {
        return queryFactory
                .delete(roomEnrollment)
                .where(roomEnrollment.user.userId.eq(userId))
                .execute();
    }

    @Override
    public RoomEnrollment findByUserId(long userId) {
        return queryFactory
                .select(roomEnrollment)
                .from(roomEnrollment)
                .where(roomEnrollment.user.userId.eq(userId))
                .fetchOne();
    }

    @Override
    public boolean checkIfMasterOfRoom(long roomId, long userId) {
        List<RoomEnrollment> roomEnrollments = queryFactory
                .select(roomEnrollment)
                .from(roomEnrollment)
                .where(roomEnrollment.room.id.eq(roomId)
                        .and(roomEnrollment.user.userId.eq(userId))
                        .and(roomEnrollment.isMaster.isTrue()))
                .fetch();

        return roomEnrollments.isEmpty();
    }
}
