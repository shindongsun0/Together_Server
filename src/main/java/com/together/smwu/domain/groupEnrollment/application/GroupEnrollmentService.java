package com.together.smwu.domain.groupEnrollment.application;

import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.groupEnrollment.dto.GroupEnrollmentRequestDto;
import com.together.smwu.domain.groupEnrollment.dto.GroupEnrollmentResponseDto;

import java.util.List;

public interface GroupEnrollmentService {

    boolean enroll(GroupEnrollmentRequestDto requestDto);

    void deleteAllUsers(long groupId, User user);

    void deleteUserFromGroup(long groupId, User user);

    List<GroupEnrollmentResponseDto> findAllByGroup(long groupId);

    List<GroupEnrollmentResponseDto> findAllByUser(long userId);

    GroupEnrollmentResponseDto findById(long id);
}
