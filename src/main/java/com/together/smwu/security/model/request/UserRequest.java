package com.together.smwu.security.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRequest {

    @Getter
    @AllArgsConstructor
    public static class Modify{
        private int msrl;
        private String name;
    }

}
