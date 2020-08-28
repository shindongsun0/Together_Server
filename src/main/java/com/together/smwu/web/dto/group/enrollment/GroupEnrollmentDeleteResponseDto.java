package com.together.smwu.web.dto.group.enrollment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupEnrollmentDeleteResponseDto {

    private Long groupId;
    private Long userId;
}
