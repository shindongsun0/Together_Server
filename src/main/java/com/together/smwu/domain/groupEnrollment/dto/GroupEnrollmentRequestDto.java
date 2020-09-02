package com.together.smwu.domain.groupEnrollment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class GroupEnrollmentRequestDto {

    @NotNull
    private Long groupId;
}
