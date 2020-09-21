package com.together.smwu.domain.room.api;

import com.together.smwu.domain.room.application.RoomService;
import com.together.smwu.domain.room.dto.RoomRequest;
import com.together.smwu.domain.room.dto.RoomResponse;
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
@Api(tags = {"3.Room"})
@RequestMapping("/api/room")
@RestController
public class RoomController {
    public static final String ROOM_URI = "/api/room";

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "Room 생성", notes = "Room을 생성한다")
    @PostMapping
    public ResponseEntity<Void> createRoom(
            @RequestBody RoomRequest request,
            @CurrentUser User user) {
        Long roomId = roomService.create(request, user);
        return ResponseEntity
                .created(URI.create(ROOM_URI + "/" + roomId))
                .build();
    }

    @ApiOperation(value = "Room 수정", notes = "Room을 수정한다.")
    @PutMapping("/{roomId}")
    public ResponseEntity<Void> updateRoom(
            @ApiParam(value = "roomId", required = true) @PathVariable Long roomId,
            @RequestBody RoomRequest request,
            @CurrentUser User user) {
        roomService.update(roomId, request, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Room 찾기", notes = "Room을 roomTitle로 찾는다.")
    @GetMapping
    public ResponseEntity<List<RoomResponse>> findByTitle(
            @ApiParam(value = "title", required = true) @RequestParam String title,
            @CurrentUser User user) {
        List<RoomResponse> responses = roomService.findByTitle(title, user);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Room 찾기", notes = "Room을 roomId로 찾는다.")
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> findByTitle(
            @ApiParam(value = "title", required = true) @PathVariable Long roomId,
            @CurrentUser User user) {
        RoomResponse roomResponse = roomService.findByRoomId(roomId, user);
        return ResponseEntity.ok(roomResponse);
    }

    @ApiOperation(value = "Room 삭제", notes = "Room을 roomId로 삭제한다.")
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(
            @ApiParam(value = "roomId", required = true) @PathVariable Long roomId,
            @CurrentUser User user) {
        roomService.deleteRoomById(roomId, user);
        return ResponseEntity.noContent().build();
    }
}
