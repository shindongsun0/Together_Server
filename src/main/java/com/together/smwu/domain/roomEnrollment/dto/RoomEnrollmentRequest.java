package com.together.smwu.domain.roomEnrollment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RoomEnrollmentRequest {

    @NotNull
    private Long roomId;

    private String credential;

    public RoomEnrollmentRequest(Long roomId, String credential) {
        this.roomId = roomId;
        this.credential = credential;
    }
}
