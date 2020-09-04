package com.together.smwu.domain.room.application;

import com.together.smwu.domain.room.dto.RoomRequest;
import com.together.smwu.domain.room.dto.RoomResponse;
import com.together.smwu.domain.user.domain.User;

import java.util.List;

public interface RoomService {

    Long create(RoomRequest requestDto, User user);

    void update(Long roomId, RoomRequest request, User user);

    List<RoomResponse> findByTitle(String roomTitle);

    RoomResponse findByRoomId(Long roomId);

    List<RoomResponse> findAllRooms();

    void deleteRoomById(Long roomId, User user);
}