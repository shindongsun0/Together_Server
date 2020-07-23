package com.together.smwu.security.controller.v1;

import com.together.smwu.advice.exception.CUserExistException;
import com.together.smwu.advice.exception.CUserNotFoundException;
import com.together.smwu.config.security.JwtTokenProvider;
import com.together.smwu.security.entity.User;
import com.together.smwu.security.model.request.SocialRequest;
import com.together.smwu.security.model.response.CommonResult;
import com.together.smwu.security.model.response.SingleResult;
import com.together.smwu.security.model.social.KakaoProfile;
import com.together.smwu.security.repository.UserJpaRepo;
import com.together.smwu.security.service.ResponseService;
import com.together.smwu.security.service.social.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "SocialRequest.Login", required = true)
            @RequestBody SocialRequest.SignIn signIn)
//            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao")
//            @PathVariable String provider,
//            @ApiParam(value = "소셜 access_token", required = true)
//            @RequestParam String accessToken)
    {
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signIn.getAccessToken());
        User user = userJpaRepo.findByUidAndProvider(String.valueOf(kakaoProfile.getId()), signIn.getProvider())
                .orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signupProvider(
            @ApiParam(value = "SocialRequest.SignUp", required = true)
            @RequestBody SocialRequest.SignUp signUp
//            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
//                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
//                                       @ApiParam(value = "이름", required = true) @RequestParam String name
    ) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signUp.getAccessToken());
        Optional<User> user = userJpaRepo.findByUidAndProvider(String.valueOf(kakaoProfile.getId()), signUp.getProvider());
        if (user.isPresent())
            throw new CUserExistException();

        User inUser = User.builder()
                .uid(String.valueOf(kakaoProfile.getId()))
                .provider(signUp.getProvider())
                .name(signUp.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userJpaRepo.save(inUser);
        return responseService.getSuccessResult();
    }
}
