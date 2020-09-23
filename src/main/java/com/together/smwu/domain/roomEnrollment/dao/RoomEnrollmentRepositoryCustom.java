package com.together.smwu.domain.roomEnrollment.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.dto.RoomDetailInfo;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface RoomEnrollmentRepositoryCustom {

    long deleteAllByRoomId(long roomId);

    long deleteAllByRoom(Room room);

    Optional<RoomEnrollment> findByUserAndRoom(long userId, long roomId);

    long deleteAllByUserId(long userId);

    RoomEnrollment findByUserId(long userId);

    boolean checkIsMasterOfRoom(long userId, long roomId);

    RoomDetailInfo getMasterUser(Room room);

    List<RoomDetailInfo> findRoomDetailInfosByUser(User user);
}
