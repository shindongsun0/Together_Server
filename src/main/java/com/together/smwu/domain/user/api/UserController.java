package com.together.smwu.domain.user.api;

import com.together.smwu.domain.security.config.jwt.JwtTokenProvider;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.security.service.ResponseService;
import com.together.smwu.domain.user.application.interfaces.UserService;
import com.together.smwu.domain.user.dao.UserRepository;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.dto.request.UserRequest;
import com.together.smwu.domain.user.dto.response.CommonResult;
import com.together.smwu.domain.user.dto.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"2. User"})
@RestController
@Slf4j
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ResponseService responseService; // 결과를 처리할 Service

    @Autowired
    public UserController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserService userService, ResponseService responseService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.responseService = responseService;
    }

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회")
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserResponse>> findAllUser(@CurrentUser User user) {
        List<UserResponse> userResponse = userService.findAllUsers();
        return ResponseEntity.ok(userResponse);
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user")
    public ResponseEntity<UserResponse> findUserById(@CurrentUser User user) {
        UserResponse userResponse = userService.findByUserId(user.getUserId());
        return ResponseEntity.ok(userResponse);
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다")
    @PutMapping(value = "/user")
    public ResponseEntity<Void> modify(
            @ApiParam(value = "UserRequest.Modify", required = true)
            @RequestBody UserRequest request,
            @CurrentUser User user) {
        userService.updateUser(request, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long userId,
            @CurrentUser User user) {
        userRepository.deleteById(userId);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}
