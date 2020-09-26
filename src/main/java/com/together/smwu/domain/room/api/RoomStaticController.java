package com.together.smwu.domain.room.api;

import com.together.smwu.domain.room.application.RoomService;
import com.together.smwu.domain.room.dto.RoomResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<RoomResponse>> findAllRooms()  {
        List<RoomResponse> responses = roomService.findAllRooms();
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Room 조회", notes = "Tag name으로 Room을 조회한다.")
    @GetMapping("/api/room/tag")
    public ResponseEntity<List<RoomResponse>> findRoomByTags(
            @ApiParam(value = "tagName", required = true) @RequestParam String tagName) {
        List<RoomResponse> responses = roomService.findByTagName(tagName);
        return ResponseEntity.ok(responses);
    }
}
