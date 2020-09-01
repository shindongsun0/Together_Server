package com.together.smwu.domain.groupEnrollment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupEnrollmentDeleteResponseDto {

    private Long groupId;
    private Long userId;
}
