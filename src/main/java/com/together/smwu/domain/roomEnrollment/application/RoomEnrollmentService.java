package com.together.smwu.domain.roomEnrollment.application;

import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentRequest;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentResponse;

import java.util.List;

public interface RoomEnrollmentService {

    Long enroll(RoomEnrollmentRequest request, User user);

    void deleteAllUsers(long roomId, User user);

    void deleteUserFromRoom(long roomId, User user);

    List<RoomEnrollmentResponse> findAllByRoomId(long roomId);

    List<RoomEnrollmentResponse> findAllByUser(long userId);

    RoomEnrollmentResponse findById(long id);
}
