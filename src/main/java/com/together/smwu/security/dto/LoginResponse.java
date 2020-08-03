package com.together.smwu.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Status status;
    private String message;
    private String error;

    public enum Status {
        SUCCESS, FAILURE
    }

    public LoginResponse(Status status, String message){
        this.status = status;
        this.message = message;    }
}
