package com.together.smwu.security.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SocialRequest {

    @Getter
    @AllArgsConstructor
    public static class SignIn {
        private String accessToken;
        private String provider;
    }

    @Getter
    @AllArgsConstructor
    public static class SignUp{
        private String accessToken;
        private String provider;
        private String name;
    }

}
