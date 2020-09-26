package com.together.smwu.domain.roomPlace.dto;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;

import java.sql.Timestamp;

public class PlaceDetailResponse {
    private Long roomPlaceId;
    private Long roomId;
    private Place place;
    private Timestamp postTime;

    public PlaceDetailResponse() {
    }

    public PlaceDetailResponse(RoomPlace entity) {
        this.roomPlaceId = entity.getId();
        this.roomId = entity.getRoom().getId();
        this.place = entity.getPlace();
        this.postTime = entity.getPostTime();
    }

    public Long getRoomPlaceId() {
        return roomPlaceId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Place getPlace() {
        return place;
    }

    public Timestamp getPostTime() {
        return postTime;
    }
}
