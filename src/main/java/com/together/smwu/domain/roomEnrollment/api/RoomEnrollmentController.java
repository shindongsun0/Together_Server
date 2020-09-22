package com.together.smwu.domain.roomEnrollment.api;

import com.together.smwu.domain.roomEnrollment.application.RoomEnrollmentService;
import com.together.smwu.domain.roomEnrollment.dto.RoomDetailResponse;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentRequest;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentResponse;
import com.together.smwu.domain.roomEnrollment.dto.UserDetailResponse;
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
@Api(tags = {"4.RoomEnrollment"})
@RequestMapping("/api/enroll")
@RestController
public class RoomEnrollmentController {
    public static final String ROOM_ENROLL_URI = "/api/enroll";

    private final RoomEnrollmentService roomEnrollmentService;

    public RoomEnrollmentController(RoomEnrollmentService roomEnrollmentService) {
        this.roomEnrollmentService = roomEnrollmentService;
    }

    @ApiOperation(value = "Room에 User 등록", notes = "Room에 User를 등록한다")
    @PostMapping
    public ResponseEntity<Void> enroll(
            @RequestBody RoomEnrollmentRequest request,
            @CurrentUser User user) {
        Long roomEnrollmentId = roomEnrollmentService.enroll(request, user);
        return ResponseEntity
                .created(URI.create(ROOM_ENROLL_URI + "/" + roomEnrollmentId))
                .build();
    }

    @ApiOperation(value = "Room의 모든 user 조회", notes = "roomId로 모든 User를 조회한다.")
    @GetMapping("/all/user/{roomId}")
    public ResponseEntity<List<UserDetailResponse>> findAllUsers(
            @ApiParam(value = "roomId", required = true) @PathVariable long roomId,
            @CurrentUser User user) {
        List<UserDetailResponse> responses = roomEnrollmentService.findAllByRoomId(roomId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "User가 속한 모든 room 조회", notes = "userId로 등록한 모든 room을 조회한다.")
    @GetMapping("/all/room/{userId}")
    public ResponseEntity<List<RoomDetailResponse>> findAllRooms(
            @ApiParam(value = "userId", required = true) @PathVariable long userId,
            @CurrentUser User user) {
        List<RoomDetailResponse> responses = roomEnrollmentService.findAllByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "RoomEnrollment 조회", notes = "roomEnrollmentId로 Room에 속한 User들을 조회한다.")
    @GetMapping("/{roomEnrollmentId}")
    public ResponseEntity<RoomEnrollmentResponse> findByRoomEnrollmentId(
            @ApiParam(value = "roomEnrollmentId", required = true) @PathVariable Long roomEnrollmentId) {
        RoomEnrollmentResponse roomEnrollmentResponse = roomEnrollmentService.findById(roomEnrollmentId);
        return ResponseEntity.ok(roomEnrollmentResponse);
    }

    @ApiOperation(value = "Room의 모든 User 삭제", notes = "Master User라면 roomId로 모든 User를 삭제한다.")
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<Void> deleteAllUsers(
            @ApiParam(value = "roomId", required = true) @PathVariable Long roomId,
            @CurrentUser User user) {
        roomEnrollmentService.deleteAllUsers(roomId, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Room의 User 삭제", notes = "roomId와 User로 그룹을 탈퇴한다.")
    @DeleteMapping("/user/{roomId}")
    public ResponseEntity<Void> deleteUserFromRoom(
            @ApiParam(value = "userId", required = true) @PathVariable long roomId,
            @CurrentUser User user) {
        roomEnrollmentService.deleteUserFromRoom(roomId, user);
        return ResponseEntity.noContent().build();
    }
}

