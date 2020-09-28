package com.together.smwu.domain.room.api;

import com.together.smwu.domain.room.application.RoomService;
import com.together.smwu.domain.room.dto.RoomStaticResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"5.RoomStatic"})
@RestController
public class RoomStaticController {
    private static final Logger logger = LoggerFactory.getLogger(RoomStaticController.class);

    private final RoomService roomService;

    public RoomStaticController(RoomService roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "모든 Room 조회", notes = "모든 Room을 조회한다.")
    @GetMapping("/api/all/room")
    public ResponseEntity<List<RoomStaticResponse>> findAllRooms()  {
        List<RoomStaticResponse> responses = roomService.findAllRooms();
        return ResponseEntity.ok(responses);
    }
}
