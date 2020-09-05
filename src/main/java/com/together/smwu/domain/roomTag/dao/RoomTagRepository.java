package com.together.smwu.domain.roomTag.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomTag.domain.RoomTag;
import com.together.smwu.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomTagRepository extends JpaRepository<RoomTag, Long> {

    Optional<RoomTag> findByRoomAndTag(Room room, Tag tag);

    List<RoomTag> findAllByRoom(Room room);

    List<RoomTag> findAllByTag(Tag tag);
}
