package com.together.smwu.domain.roomEnrollment.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.dto.RoomDetailInfo;
import com.together.smwu.domain.room.dto.RoomWithMasterInfo;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface RoomEnrollmentRepositoryCustom {

    long deleteAllByRoomId(Long roomId);

    long deleteAllByRoom(Room room);

    Optional<RoomEnrollment> findByUserAndRoom(Long userId, Long roomId);

    long deleteAllByUserId(Long userId);

    Optional<RoomEnrollment> findByUserId(Long userId);

    boolean checkIsMasterOfRoom(Long userId, Long roomId);

    RoomDetailInfo getRoomDetailInfo(Room room, Long userId);

    RoomWithMasterInfo getRoomMasterInfo(Room room);

    List<RoomDetailInfo> findRoomDetailInfosByUser(User user);
}
