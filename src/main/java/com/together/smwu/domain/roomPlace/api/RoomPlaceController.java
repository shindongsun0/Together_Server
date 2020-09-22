package com.together.smwu.domain.roomPlace.api;

import com.together.smwu.domain.roomPlace.application.RoomPlaceService;
import com.together.smwu.domain.roomPlace.dto.RoomPlaceRequest;
import com.together.smwu.domain.roomPlace.dto.RoomPlaceResponse;
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
@Api(tags = {"5.RoomPlace"})
@RequestMapping("/api/room/place")
@RestController
public class RoomPlaceController {
    public static final String ROOM_PLACE_URI = "/api/room/place";

    private final RoomPlaceService roomPlaceService;

    public RoomPlaceController(RoomPlaceService roomPlaceService) {
        this.roomPlaceService = roomPlaceService;
    }

    @ApiOperation(value = "Room에 Place 추가", notes = "Room에 Place를 추가한다.")
    @PostMapping
    public ResponseEntity<Void> addPlaceInRoom(
            @RequestBody RoomPlaceRequest request,
            @CurrentUser User user) {
        Long roomPlaceId = roomPlaceService.save(request, user);
        return ResponseEntity
                .created(URI.create(ROOM_PLACE_URI + "/" + roomPlaceId))
                .build();
    }

    @ApiOperation(value = "Room의 모든 Place조회", notes = "roomId로 모든 Place를 조회한다.")
    @GetMapping("/all/room/{placeId}")
    public ResponseEntity<List<RoomPlaceResponse>> findAllRooms(
            @ApiParam(value = "placeId", required = true) @PathVariable Long placeId,
            @CurrentUser User user) {
        List<RoomPlaceResponse> responses = roomPlaceService.findAllByPlaceId(placeId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Place가 속한 모든 Room조회", notes = "placeId로 모든 Room을 조회한다.")
    @GetMapping("/all/place/{roomId}")
    public ResponseEntity<List<RoomPlaceResponse>> findAllPlaces(
            @ApiParam(value = "roomId", required = true) @PathVariable Long roomId,
            @CurrentUser User user) {
        List<RoomPlaceResponse> responses = roomPlaceService.findAllByRoomId(roomId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Room에 속한 Place조회")
    @GetMapping("/{roomPlaceId}")
    public ResponseEntity<RoomPlaceResponse> findByRoomPlaceId(
            @ApiParam(value = "roomPlaceId", required = true) @PathVariable Long roomPlaceId){
        RoomPlaceResponse response = roomPlaceService.findByRoomPlaceId(roomPlaceId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Place가 속한 모든 Room삭제")
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<Void> deleteAllPlaces(
            @ApiParam(value = "roomId", required = true) @PathVariable Long roomId,
            @CurrentUser User user) {
        roomPlaceService.deleteAllPlacesByRoomId(roomId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Room이 속한 모든 Place삭제")
    @DeleteMapping("/place/{placeId}")
    public ResponseEntity<Void> deleteAllRooms(
            @ApiParam(value = "placeId", required = true) @PathVariable Long placeId,
            @CurrentUser User user) {
        roomPlaceService.deleteAllRoomsByPlaceId(placeId);
        return ResponseEntity.noContent().build();
    }
}
