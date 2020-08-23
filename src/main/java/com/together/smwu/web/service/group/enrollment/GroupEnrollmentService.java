package com.together.smwu.web.service.group.enrollment;

import com.together.smwu.web.domain.user.User;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentRequestDto;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentResponseDto;

import java.util.List;

public interface GroupEnrollmentService {

    boolean enroll(GroupEnrollmentRequestDto requestDto);

    void deleteAllUsers(long groupId, User user);

    void deleteUserFromGroup(long groupId, User user);

    List<GroupEnrollmentResponseDto> findAllByGroup(long groupId);

    List<GroupEnrollmentResponseDto> findAllByUser(long userId);

    GroupEnrollmentResponseDto findById(long id);
}
