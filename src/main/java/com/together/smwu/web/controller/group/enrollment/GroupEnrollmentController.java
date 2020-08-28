package com.together.smwu.web.controller.group.enrollment;

import com.together.smwu.config.security.CurrentUser;
import com.together.smwu.web.domain.user.User;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentDeleteResponseDto;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentRequestDto;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentResponseDto;
import com.together.smwu.web.service.group.enrollment.GroupEnrollmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"4.GroupEnrollment"})
@RestController
@Slf4j
public class GroupEnrollmentController {

    private GroupEnrollmentService groupEnrollmentService;

    public GroupEnrollmentController(GroupEnrollmentService groupEnrollmentService) {
        this.groupEnrollmentService = groupEnrollmentService;
    }

    @ApiOperation(value = "Group에 User 등록", notes = "Group에 User를 등록한다")
    @PostMapping(value = "/api/group/enroll")
    public boolean enroll(
            @RequestBody GroupEnrollmentRequestDto requestDto,
            @CurrentUser User user) {
        return groupEnrollmentService.enroll(requestDto);
    }

    @ApiOperation(value = "Group의 모든 user 조회", notes = "groupId로 모든 User를 조회한다.")
    @GetMapping(value = "/api/group/enroll/all/{groupId}")
    public List<GroupEnrollmentResponseDto> findAllUsers(
            @ApiParam(value = "groupId", required = true) @PathVariable long groupId,
            @CurrentUser User user) {

        return groupEnrollmentService.findAllByGroup(groupId);
    }

    @ApiOperation(value = "User가 속한 모든 group 조회", notes = "userId로 등록한 모든 group을 조회한다.")
    @GetMapping(value = "/api/group/enroll/all/{userId}")
    public List<GroupEnrollmentResponseDto> findAllGroups(
            @ApiParam(value = "userId", required = true) @PathVariable long userId,
            @CurrentUser User user) {

        return groupEnrollmentService.findAllByUser(userId);
    }

    @GetMapping("/api/group/enroll/{id}")
    public GroupEnrollmentResponseDto findById(
            @ApiParam(value = "groupEnrollmentId", required = true) @PathVariable Long id) {

        return groupEnrollmentService.findById(id);
    }

    @ApiOperation(value = "Group의 모든 User 삭제", notes = "Master User라면 groupId로 모든 User를 삭제한다.")
    @DeleteMapping(value = "/api/group/enroll/delete/{groupId}")
    public GroupEnrollmentDeleteResponseDto deleteAllUsers(
            @ApiParam(value = "groupId", required = true) @PathVariable long groupId,
            @CurrentUser User user) {

        groupEnrollmentService.deleteAllUsers(groupId, user);

        return GroupEnrollmentDeleteResponseDto.builder()
                .groupId(groupId)
                .build();
    }

    @ApiOperation(value = "Group의 User 삭제", notes = "groupId와 User로 그룹을 탈퇴한다.")
    @DeleteMapping(value = "/api/group/enroll/{groupId}/delete")
    public GroupEnrollmentDeleteResponseDto deleteUserFromGroup(
            @ApiParam(value = "userId", required = true) @PathVariable long groupId,
            @CurrentUser User user) {

        groupEnrollmentService.deleteUserFromGroup(groupId, user);

        return GroupEnrollmentDeleteResponseDto.builder()
                .userId(user.getMsrl())
                .groupId(groupId)
                .build();
    }
}

