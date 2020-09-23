package com.together.smwu.domain.roomEnrollment.application;

import com.together.smwu.domain.roomEnrollment.dto.RoomDetailResponse;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentRequest;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentResponse;
import com.together.smwu.domain.roomEnrollment.dto.UserDetailResponse;
import com.together.smwu.domain.user.domain.User;

import java.util.List;

public interface RoomEnrollmentService {

    Long enroll(RoomEnrollmentRequest request, User user);

    void deleteAllUsers(Long roomId, User user);

    void deleteUserFromRoom(Long roomId, User user);

    List<UserDetailResponse> findAllByRoomId(Long roomId);

    List<RoomDetailResponse> findAllByUser(Long userId);

    RoomEnrollmentResponse findById(Long id);

    RoomEnrollmentResponse findByRoomAndUser(Long userId, Long roomId);
}
