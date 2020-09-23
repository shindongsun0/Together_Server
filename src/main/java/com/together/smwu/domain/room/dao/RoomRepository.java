package com.together.smwu.domain.room.dao;

import com.together.smwu.domain.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    Room save(Room room);

    Optional<Room> findById(Long id);

    List<Room> findByTitle(String title);
}
