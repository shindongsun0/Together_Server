package com.together.smwu.web.service.group.enrollment;

import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentListResponseDto;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentRequestDto;
import com.together.smwu.web.repository.user.User;

import java.util.List;

public interface GroupEnrollmentService {

    boolean enroll(GroupEnrollmentRequestDto requestDto);

    void deleteAllUsers(long groupId, User user);

    void deleteUserFromGroup(long userId);

    List<GroupEnrollmentListResponseDto> findAllByGroup(long groupId);

    List<GroupEnrollmentListResponseDto> findAllByUser(long userId);
}
