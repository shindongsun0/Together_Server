package com.together.smwu.web.dto.group.enrollment;

import com.together.smwu.web.domain.group.enrollment.GroupEnrollment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class GroupEnrollmentListResponseDto {

    private Long groupEnrollmentId;
    private Long groupId;
    private Long userMsrl;
    private Timestamp date;
    private boolean isMaster;

    public GroupEnrollmentListResponseDto(GroupEnrollment entity){
        this.groupEnrollmentId = entity.getGroupEnrollmentId();
        this.groupId = entity.getGroup().getId();
        this.userMsrl = entity.getUser().getMsrl();
        this.date = entity.getDate();
        this.isMaster = entity.getIsMaster();
    }
}
