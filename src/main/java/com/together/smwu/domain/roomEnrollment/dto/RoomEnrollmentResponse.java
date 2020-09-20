package com.together.smwu.domain.roomEnrollment.dto;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class RoomEnrollmentResponse {

    private Long roomEnrollmentId;
    private RoomInfo room;
    private Long userId;
    private Timestamp enrolledAt;
    private Boolean isMaster;

    public RoomEnrollmentResponse(RoomEnrollment entity) {
        this.roomEnrollmentId = entity.getRoomEnrollmentId();
        this.room = getRoomInfoFromRoom(entity.getRoom());
        this.userId = entity.getUser().getUserId();
        this.enrolledAt = entity.getEnrolledAt();
        this.isMaster = entity.getIsMaster();
    }

    private RoomInfo getRoomInfoFromRoom(Room room) {
        return new RoomInfo(room);
    }
}
