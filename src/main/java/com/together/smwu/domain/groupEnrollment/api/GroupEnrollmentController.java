package com.together.smwu.domain.groupEnrollment.api;

import com.together.smwu.domain.groupEnrollment.application.GroupEnrollmentService;
import com.together.smwu.domain.groupEnrollment.dto.GroupEnrollmentRequestDto;
import com.together.smwu.domain.groupEnrollment.dto.GroupEnrollmentResponseDto;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"4.GroupEnrollment"})
@RestController
public class GroupEnrollmentController {
    public static final String GROUP_ENROLL_URI = "/api/group/enroll";

    private final GroupEnrollmentService groupEnrollmentService;

    public GroupEnrollmentController(GroupEnrollmentService groupEnrollmentService) {
        this.groupEnrollmentService = groupEnrollmentService;
    }

    @ApiOperation(value = "Group에 User 등록", notes = "Group에 User를 등록한다")
    @PostMapping(value = GROUP_ENROLL_URI)
    public ResponseEntity<Void> enroll(
            @RequestBody GroupEnrollmentRequestDto requestDto,
            @CurrentUser User user) {
        Long groupEnrollmentId = groupEnrollmentService.enroll(requestDto, user);
        return ResponseEntity
                .created(URI.create(GROUP_ENROLL_URI + "/" + groupEnrollmentId))
                .build();
    }

    @ApiOperation(value = "Group의 모든 user 조회", notes = "groupId로 모든 User를 조회한다.")
    @GetMapping(value = "/api/group/enroll/all/{groupId}")
    public ResponseEntity<List<GroupEnrollmentResponseDto>> findAllUsers(
            @ApiParam(value = "groupId", required = true) @PathVariable long groupId,
            @CurrentUser User user) {
        List<GroupEnrollmentResponseDto> responses = groupEnrollmentService.findAllByGroup(groupId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "User가 속한 모든 group 조회", notes = "userId로 등록한 모든 group을 조회한다.")
    @GetMapping(value = "/api/group/enroll/all/{userId}")
    public ResponseEntity<List<GroupEnrollmentResponseDto>> findAllGroups(
            @ApiParam(value = "userId", required = true) @PathVariable long userId,
            @CurrentUser User user) {
        List<GroupEnrollmentResponseDto> responses = groupEnrollmentService.findAllByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "GroupEnrollment 조회", notes = "groupEnrollmentId로 Group에 속한 User들을 조회한다.")
    @GetMapping("/api/group/enroll/{groupEnrollmentId}")
    public ResponseEntity<GroupEnrollmentResponseDto> findByGroupEnrollmentId(
            @ApiParam(value = "groupEnrollmentId", required = true) @PathVariable Long groupEnrollmentId) {
        GroupEnrollmentResponseDto groupEnrollmentResponse = groupEnrollmentService.findById(groupEnrollmentId);
        return ResponseEntity.ok(groupEnrollmentResponse);
    }

    @ApiOperation(value = "Group의 모든 User 삭제", notes = "Master User라면 groupId로 모든 User를 삭제한다.")
    @DeleteMapping(value = "/api/group/enroll/delete/{groupId}")
    public ResponseEntity<Void> deleteAllUsers(
            @ApiParam(value = "groupId", required = true) @PathVariable long groupId,
            @CurrentUser User user) {
        groupEnrollmentService.deleteAllUsers(groupId, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Group의 User 삭제", notes = "groupId와 User로 그룹을 탈퇴한다.")
    @DeleteMapping(value = "/api/group/enroll/{groupId}/delete")
    public ResponseEntity<Void> deleteUserFromGroup(
            @ApiParam(value = "userId", required = true) @PathVariable long groupId,
            @CurrentUser User user) {
        groupEnrollmentService.deleteUserFromGroup(groupId, user);
        return ResponseEntity.noContent().build();
    }
}

