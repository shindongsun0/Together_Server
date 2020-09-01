package com.together.smwu.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    public static class Modify{
        private String name;
    }

}
