package com.together.smwu.domain.roomEnrollment.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;

public interface RoomEnrollmentRepositoryCustom {

    long deleteAllByRoomId(long roomId);

    long deleteAllByRoom(Room room);

    long deleteAllByUserId(long userId);

    RoomEnrollment findByUserId(long userId);

    boolean checkIsMasterOfRoom(long userId, long roomId);
}
