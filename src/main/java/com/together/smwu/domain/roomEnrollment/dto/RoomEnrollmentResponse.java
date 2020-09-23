package com.together.smwu.domain.roomEnrollment.dto;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class RoomEnrollmentResponse {

    private final Long roomEnrollmentId;
    private final RoomInfo room;
    private final Long userId;
    private final Timestamp enrolledAt;
    private final Boolean isMaster;

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
