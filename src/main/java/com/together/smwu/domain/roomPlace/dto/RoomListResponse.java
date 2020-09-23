package com.together.smwu.domain.roomPlace.dto;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;

import java.util.List;
import java.util.stream.Collectors;

public class RoomListResponse {
    private List<Long> roomId;

    public RoomListResponse(List<Long> roomId) {
        this.roomId = roomId;
    }

    public RoomListResponse() {
    }


    public List<Long> getRoomId() {
        return this.roomId;
    }

    public static RoomListResponse from(List<RoomPlace> roomPlaces) {
        List<Long> roomIds = roomPlaces.stream()
                .map(RoomPlace::getRoom)
                .map(Room::getId)
                .collect(Collectors.toList());
        return new RoomListResponse(roomIds);
    }
}
