package com.together.smwu.domain.roomEnrollment.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomEnrollmentRepository extends JpaRepository<RoomEnrollment, Long>, RoomEnrollmentRepositoryCustom {

    Optional<RoomEnrollment> findByUserAndRoom(User user, Room room);

    List<RoomEnrollment> findAllByUser(User user);

    List<RoomEnrollment> findAllByRoom(Room room);
}
