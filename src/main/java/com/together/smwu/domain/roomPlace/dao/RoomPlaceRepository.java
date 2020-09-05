package com.together.smwu.domain.roomPlace.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;
import com.together.smwu.domain.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomPlaceRepository extends JpaRepository<RoomPlace, Long> {

    Optional<RoomPlace> findByRoomAndPlace(Room room, Place place);

    List<RoomPlace> findAllByRoom(Room room);

    List<RoomPlace> findAllByPlace(Place place);
}
