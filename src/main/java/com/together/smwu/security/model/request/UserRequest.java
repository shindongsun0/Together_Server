package com.together.smwu.security.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    public static class Modify{
        private String name;
    }

}
