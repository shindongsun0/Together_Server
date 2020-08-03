package com.together.smwu.security.service.security;

import com.together.smwu.security.dto.LoginResponse;
import com.together.smwu.security.entity.User;
import com.together.smwu.security.model.request.SignInRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    LoginResponse login(SignInRequest loginRequest, HttpServletResponse response, String accessToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    User getUser();

    void logout(HttpServletRequest request, HttpServletResponse response);
}
