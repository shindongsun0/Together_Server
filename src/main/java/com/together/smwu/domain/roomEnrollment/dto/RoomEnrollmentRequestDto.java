package com.together.smwu.domain.roomEnrollment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RoomEnrollmentRequestDto {

    @NotNull
    private Long roomId;
}
