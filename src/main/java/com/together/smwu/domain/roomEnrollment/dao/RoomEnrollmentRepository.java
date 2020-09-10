package com.together.smwu.domain.roomEnrollment.dao;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomEnrollmentRepository extends JpaRepository<RoomEnrollment, Long>, RoomEnrollmentRepositoryCustom {

    List<RoomEnrollment> findAllByUser(User user);

    List<RoomEnrollment> findAllByRoom(Room room);
}
