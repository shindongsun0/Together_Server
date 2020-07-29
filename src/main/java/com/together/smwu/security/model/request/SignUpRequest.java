package com.together.smwu.security.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String accessToken;
    private String provider;
    private String name;
}
