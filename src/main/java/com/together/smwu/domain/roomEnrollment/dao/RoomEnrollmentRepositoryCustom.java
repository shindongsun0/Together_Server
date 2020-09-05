package com.together.smwu.domain.roomEnrollment.dao;

import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;

public interface RoomEnrollmentRepositoryCustom {

    long deleteAllByRoomId(long roomId);

    long deleteAllByUserId(long userId);

    RoomEnrollment findByUserId(long userId);

    boolean checkIfMasterOfRoom(long roomId, long userId);
}
