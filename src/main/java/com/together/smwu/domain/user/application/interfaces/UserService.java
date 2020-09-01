package com.together.smwu.domain.user.application.interfaces;

import com.together.smwu.domain.user.dto.request.SignUpRequest;
import com.together.smwu.domain.user.dto.response.LoginResponse;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.dto.request.SignInRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    LoginResponse login(SignInRequest loginRequest, HttpServletResponse response, String accessToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    User getUser();

    void logout(HttpServletRequest request, HttpServletResponse response);

    void signUp(SignUpRequest signUpRequest);
}
