package com.together.smwu.domain.roomEnrollment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomEnrollmentDeleteResponse {

    private Long roomId;
    private Long userId;
}
