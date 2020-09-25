package com.together.smwu.global.advice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // User
    USER_ALREADY_EXISTS(400, "U001", "User is already registered"),
    USER_LOGGED_OUT(400, "U002", "Users' JWT Token Expired"),

    // Room
    ROOM_ALREADY_EXISTS(400, "GR001", "Room is already registered."),
    ROOM_NOT_AUTHORIZED(403, "GR002", "Room Access is Denied"),

    // Place
    PLACE_ALREADY_EXISTS(400, "P001", "Place is already stored.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    }
