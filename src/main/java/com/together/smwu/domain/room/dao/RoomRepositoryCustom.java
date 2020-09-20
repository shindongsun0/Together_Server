package com.together.smwu.domain.room.dao;

import com.together.smwu.domain.room.domain.Room;

import java.util.List;

public interface RoomRepositoryCustom {

    List<Room> getAllRooms();

    List<Room> findByTagName(String tagName);
}
