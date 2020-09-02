package com.together.smwu.domain.groupEnrollment.dto;

import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class GroupEnrollmentResponseDto {

    private Long groupEnrollmentId;
    private Long groupId;
    private Long userId;
    private Timestamp enrolledAt;
    private Boolean isMaster;

    public GroupEnrollmentResponseDto(GroupEnrollment entity){
        this.groupEnrollmentId = entity.getGroupEnrollmentId();
        this.groupId = entity.getGroup().getId();
        this.userId = entity.getUser().getMsrl();
        this.enrolledAt = entity.getEnrolledAt();
        this.isMaster = entity.getIsMaster();
    }
}
