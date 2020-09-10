package com.together.smwu.domain.roomEnrollment.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.room.domain.QRoom;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomEnrollment.domain.QRoomEnrollment;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.user.domain.QUser;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.together.smwu.domain.roomEnrollment.domain.QRoomEnrollment.roomEnrollment;

@RequiredArgsConstructor
public class RoomEnrollmentRepositoryImpl implements RoomEnrollmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QRoomEnrollment re = roomEnrollment;
    private final QRoom r = QRoom.room;
    private final QUser u = QUser.user;

    @Override
    public long deleteAllByRoomId(long roomId) {
        return queryFactory
                .delete(roomEnrollment)
                .where(roomEnrollment.room.id.eq(roomId))
                .execute();
    }

    @Override
    public long deleteAllByRoom(Room room) {
        return queryFactory.delete(re)
                .where(re.room.eq(room))
                .execute();
    }

    @Override
    public Optional<RoomEnrollment> findByUserAndRoom(long userId, long roomId) {
        return Optional.ofNullable(queryFactory.selectFrom(re)
                .where(re.room.id.eq(roomId).and(re.user.userId.eq(userId)))
                .fetchOne());
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
    public boolean checkIsMasterOfRoom(long userId, long roomId) {
        List<RoomEnrollment> roomEnrollments = queryFactory.selectFrom(re)
                .join(re.room, r).fetchJoin()
                .join(re.user, u).fetchJoin()
                .where(re.user.userId.eq(userId).and(re.room.id.eq(roomId)))
                .fetch();

        return !roomEnrollments.isEmpty();
    }
}
