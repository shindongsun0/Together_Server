package com.together.smwu.domain.roomPlace.dto;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;

import java.sql.Timestamp;

public class RoomPlaceResponse {
    private Long roomPlaceId;
    private Long roomId;
    private Place place;
    private Timestamp postTime;

    public RoomPlaceResponse() {
    }

    public RoomPlaceResponse(RoomPlace entity) {
        this.roomPlaceId = entity.getId();
        this.roomId = entity.getRoom().getId();
        this.place = entity.getPlace();
        this.postTime = entity.getPostTime();
    }

    public Long getRoomPlaceId() {
        return this.roomPlaceId;
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public Place getPlace() {
        return this.place;
    }

    public Timestamp getPostTime() {
        return this.postTime;
    }
}
