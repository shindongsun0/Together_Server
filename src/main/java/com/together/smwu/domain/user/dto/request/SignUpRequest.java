package com.together.smwu.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String accessToken;
    private String provider;
    private String name;
    private String profileImage;

    private SignUpRequest(String accessToken, String provider, String name,
                          String profileImage) {
        this.accessToken = accessToken;
        this.provider = provider;
        this.name = name;
        this.profileImage = profileImage;
    }
}
