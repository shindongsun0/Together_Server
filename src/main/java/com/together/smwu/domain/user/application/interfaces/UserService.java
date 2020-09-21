package com.together.smwu.domain.user.application.interfaces;

import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.dto.request.SignInRequest;
import com.together.smwu.domain.user.dto.request.SignUpRequest;
import com.together.smwu.domain.user.dto.request.UserRequest;
import com.together.smwu.domain.user.dto.response.LoginResponse;
import com.together.smwu.domain.user.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {

    LoginResponse login(SignInRequest loginRequest, HttpServletResponse response, String accessToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    UserResponse findByUserId(Long userId);

    List<UserResponse> findAllUsers();

    void updateUser(UserRequest request, User user);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void signUp(SignUpRequest signUpRequest);

    void updateProfileImage(MultipartFile multipartFile, User user) throws IOException;
}
