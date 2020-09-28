package com.together.smwu.domain.roomEnrollment.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.room.domain.QRoom;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.dto.Master;
import com.together.smwu.domain.room.dto.RoomDetailInfo;
import com.together.smwu.domain.room.dto.RoomWithMasterInfo;
import com.together.smwu.domain.roomEnrollment.domain.QRoomEnrollment;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.user.domain.QUser;
import com.together.smwu.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RoomEnrollmentRepositoryImpl implements RoomEnrollmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QRoomEnrollment roomEnrollment = QRoomEnrollment.roomEnrollment;
    private final QRoom room = QRoom.room;
    private final QUser user = QUser.user;

    @Override
    public long deleteAllByRoomId(Long roomId) {
        return queryFactory
                .delete(roomEnrollment)
                .where(roomEnrollment.room.id.eq(roomId))
                .execute();
    }

    @Override
    public long deleteAllByRoom(Room room) {
        return queryFactory.delete(roomEnrollment)
                .where(roomEnrollment.room.eq(room).and(roomEnrollment.isMaster.isFalse()))
                .execute();
    }

    @Override
    public Optional<RoomEnrollment> findByUserAndRoom(Long userId, Long roomId) {
        return Optional.ofNullable(queryFactory.selectFrom(roomEnrollment)
                .where(roomEnrollment.room.id.eq(roomId)
                        .and(roomEnrollment.user.userId.eq(userId)))
                .fetchOne());
    }

    @Override
    public long deleteAllByUserId(Long userId) {
        return queryFactory
                .delete(roomEnrollment)
                .where(roomEnrollment.user.userId.eq(userId))
                .execute();
    }

    @Override
    public Optional<RoomEnrollment> findByUserId(Long userId) {
        return Optional.ofNullable(queryFactory
                .select(roomEnrollment)
                .from(roomEnrollment)
                .where(roomEnrollment.user.userId.eq(userId))
                .fetchOne());
    }

    @Override
    public boolean checkIsMasterOfRoom(Long userId, Long roomId) {
        List<RoomEnrollment> roomEnrollments = queryFactory.selectFrom(roomEnrollment)
                .join(roomEnrollment.room, room).fetchJoin()
                .join(roomEnrollment.user, user).fetchJoin()
                .where(roomEnrollment.user.userId.eq(userId)
                        .and(roomEnrollment.room.id.eq(roomId)))
                .fetch();

        return !roomEnrollments.isEmpty();
    }

    public RoomDetailInfo getRoomDetailInfo(Room targetRoom, Long userId) {
        Master master = queryFactory
                .select(Projections.constructor(Master.class, roomEnrollment.user.name, roomEnrollment.user.profileImage))
                .from(roomEnrollment)
                .leftJoin(roomEnrollment.user, user)
                .leftJoin(roomEnrollment.room, room)
                .where(roomEnrollment.room.id.eq(targetRoom.getId()).and(roomEnrollment.isMaster.isTrue()))
                .fetchOne();

        Optional<RoomEnrollment> enrolledRoom = Optional.ofNullable(queryFactory
                .selectFrom(roomEnrollment)
                .where(roomEnrollment.room.id.eq(targetRoom.getId()).and(roomEnrollment.user.userId.eq(userId)))
                .fetchOne());
        return new RoomDetailInfo(targetRoom, master, enrolledRoom.isPresent());
    }

    @Override
    public RoomWithMasterInfo getRoomMasterInfo(Room targetRoom) {
        Master master = queryFactory
                .select(Projections.constructor(Master.class, roomEnrollment.user.name, roomEnrollment.user.profileImage))
                .from(roomEnrollment)
                .leftJoin(roomEnrollment.user, user)
                .leftJoin(roomEnrollment.room, room)
                .where(roomEnrollment.room.id.eq(targetRoom.getId()).and(roomEnrollment.isMaster.isTrue()))
                .fetchOne();
        return new RoomWithMasterInfo(targetRoom, master);
    }

    public List<RoomDetailInfo> findRoomDetailInfosByUser(User user) {
        List<RoomEnrollment> roomEnrollments = queryFactory
                .selectFrom(roomEnrollment)
                .where(roomEnrollment.user.eq(user))
                .fetch();
        return roomEnrollments.stream()
                .map(RoomEnrollment::getRoom)
                .map(room -> getRoomDetailInfo(room, user.getUserId()))
                .collect(Collectors.toList());

    }
}
