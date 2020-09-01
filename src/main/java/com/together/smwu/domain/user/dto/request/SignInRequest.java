package com.together.smwu.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequest {
    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("provider")
    private String provider;
}
