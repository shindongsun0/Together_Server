package com.together.smwu.security.controller.v1;

import com.together.smwu.security.model.request.SignInRequest;
import com.together.smwu.security.model.request.SignUpRequest;
import com.together.smwu.security.model.response.CommonResult;
import com.together.smwu.security.model.response.LoginResponse;
import com.together.smwu.web.repository.user.UserJpaRepo;
import com.together.smwu.security.service.ResponseService;
import com.together.smwu.web.service.user.interfaces.UserService;
import com.together.smwu.security.service.social.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = {"1. Sign"})
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;
    private final UserService userService;
    private final KakaoService kakaoService;

    @Autowired
    public SignController(UserJpaRepo userJpaRepo, ResponseService responseService,
                          UserService userService, KakaoService kakaoService) {
        this.userJpaRepo = userJpaRepo;
        this.responseService = responseService;
        this.userService = userService;
        this.kakaoService = kakaoService;
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public ResponseEntity<LoginResponse> signinByProvider(
            @ApiParam(value = "SocialRequest.Login", required = true)
            @RequestBody SignInRequest signIn,
            @CookieValue(name = "X-AUTH-TOKEN", required = false) String accessToken,
            HttpServletResponse httpServletResponse) {

        LoginResponse loginResponse = userService.login(signIn, httpServletResponse, accessToken);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signupProvider(
            @ApiParam(value = "SignUpRequest.SignUp", required = true)
            @RequestBody SignUpRequest signUp
    ) {
        userService.signUp(signUp);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃시 토큰을 만료시킨다.")
    @GetMapping("/logout")
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
    }
}
