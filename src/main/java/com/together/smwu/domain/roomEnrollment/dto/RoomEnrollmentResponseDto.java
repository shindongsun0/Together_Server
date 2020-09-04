package com.together.smwu.domain.roomEnrollment.dto;

import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class RoomEnrollmentResponseDto {

    private Long roomEnrollmentId;
    private Long roomId;
    private Long userId;
    private Timestamp enrolledAt;
    private Boolean isMaster;

    public RoomEnrollmentResponseDto(RoomEnrollment entity){
        this.roomEnrollmentId = entity.getRoomEnrollmentId();
        this.roomId = entity.getRoom().getId();
        this.userId = entity.getUser().getUserId();
        this.enrolledAt = entity.getEnrolledAt();
        this.isMaster = entity.getIsMaster();
    }
}
