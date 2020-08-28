package com.together.smwu.web.dto.group.enrollment;

import com.together.smwu.web.domain.group.enrollment.GroupEnrollment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class GroupEnrollmentResponseDto {

    private Long groupEnrollmentId;
    private Long groupId;
    private Long userId;
    private Timestamp date;
    private Boolean isMaster;

    public GroupEnrollmentResponseDto(GroupEnrollment entity){
        this.groupEnrollmentId = entity.getGroupEnrollmentId();
        this.groupId = entity.getGroup().getId();
        this.userId = entity.getUser().getMsrl();
        this.date = entity.getDate();
        this.isMaster = entity.getIsMaster();
    }
}
