package com.together.smwu.domain.roomEnrollment.application;

import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentRequestDto;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentResponseDto;

import java.util.List;

public interface RoomEnrollmentService {

    Long enroll(RoomEnrollmentRequestDto requestDto, User user);

    void deleteAllUsers(long roomId, User user);

    void deleteUserFromRoom(long roomId, User user);

    List<RoomEnrollmentResponseDto> findAllByRoomId(long roomId);

    List<RoomEnrollmentResponseDto> findAllByUser(long userId);

    RoomEnrollmentResponseDto findById(long id);
}
