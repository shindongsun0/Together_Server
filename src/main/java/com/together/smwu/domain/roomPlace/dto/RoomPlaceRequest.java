package com.together.smwu.domain.roomPlace.dto;

public class RoomPlaceRequest {

    private Long roomId;

    private Long placeId;

    public RoomPlaceRequest() {
    }

    public RoomPlaceRequest(Long roomId, Long placeId) {
        this.roomId = roomId;
        this.placeId = placeId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getPlaceId() {
        return placeId;
    }
}
