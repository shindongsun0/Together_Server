package com.together.smwu.domain.roomEnrollment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomEnrollmentDeleteResponse {

    private final Long roomId;
    private final Long userId;
}
