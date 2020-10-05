package com.together.smwu.domain.roomPlace.dao;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomPlaceRepository extends JpaRepository<RoomPlace, Long> {

    List<RoomPlace> findAllByRoom(Room room);

    List<RoomPlace> findAllByPlace(Place place);

    void deleteByRoom(Room room);
}
