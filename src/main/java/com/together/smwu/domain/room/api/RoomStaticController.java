package com.together.smwu.domain.room.api;

import com.together.smwu.domain.room.application.RoomService;
import com.together.smwu.domain.room.dto.RoomResponse;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"3.Room"})
@RestController
public class RoomStaticController {

    private final RoomService roomService;

    public RoomStaticController(RoomService roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "모든 Room 조회", notes = "모든 Room을 조회한다.")
    @GetMapping("/api/room/all")
    public ResponseEntity<List<RoomResponse>> findAllRooms(
            @CurrentUser User user) {
        List<RoomResponse> roomRespons = roomService.findAllRooms();
        return ResponseEntity.ok(roomRespons);
    }
}
