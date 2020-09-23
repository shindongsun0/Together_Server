package com.together.smwu.domain.roomPlace.application;

import com.together.smwu.domain.roomPlace.dto.PlaceDetailResponse;
import com.together.smwu.domain.roomPlace.dto.RoomListResponse;
import com.together.smwu.domain.roomPlace.dto.RoomPlaceRequest;
import com.together.smwu.domain.roomPlace.dto.RoomPlaceResponse;
import com.together.smwu.domain.user.domain.User;

import java.util.List;

public interface RoomPlaceService {

    Long save(RoomPlaceRequest request, User user);

    RoomListResponse findAllByPlaceId(Long placeId);

    List<PlaceDetailResponse> findAllByRoomId(Long roomId);

    void deleteAllPlacesByRoomId(Long roomId);

    void deleteAllRoomsByPlaceId(Long placeId);

    RoomPlaceResponse findByRoomPlaceId(Long roomPlaceId);
}
