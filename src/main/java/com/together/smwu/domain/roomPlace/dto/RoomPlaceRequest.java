package com.together.smwu.domain.roomPlace.dto;

public class RoomPlaceRequest {

    private Long roomId;

    private Long placeId;

    private String comment;

    public RoomPlaceRequest() {
    }

    public RoomPlaceRequest(Long roomId, Long placeId, String comment) {
        this.roomId = roomId;
        this.placeId = placeId;
        this.comment = comment;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public String getComment() {
        return comment;
    }
}
