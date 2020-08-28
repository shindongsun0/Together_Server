package com.together.smwu.web.dto.group.enrollment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupEnrollmentDeleteResponseDto {

    private Long groupId;
    private Long userId;

    @Builder
    public GroupEnrollmentDeleteResponseDto(Long groupId){
        this.groupId = groupId;
    }

    @Builder
    public GroupEnrollmentDeleteResponseDto(Long groupId, Long userId){
        this.groupId = groupId;
        this.userId = userId;
    }

}
