package com.together.smwu.controller.v1;

import com.together.smwu.advice.exception.CUserExistException;
import com.together.smwu.advice.exception.CUserNotFoundException;
import com.together.smwu.config.security.JwtTokenProvider;
import com.together.smwu.entity.User;
import com.together.smwu.model.response.CommonResult;
import com.together.smwu.model.response.SingleResult;
import com.together.smwu.model.social.KakaoProfile;
import com.together.smwu.repository.UserJpaRepo;
import com.together.smwu.service.ResponseService;
import com.together.smwu.service.social.KakaoService;
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
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);
        User user = userJpaRepo.findByUidAndProvider(String.valueOf(kakaoProfile.getId()), provider).orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "이름", required = true) @RequestParam String name){

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);
        Optional<User> user = userJpaRepo.findByUidAndProvider(String.valueOf(kakaoProfile.getId()), provider);
        if(user.isPresent())
            throw new CUserExistException();

        User inUser = User.builder()
                .uid(String.valueOf(kakaoProfile.getId()))
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userJpaRepo.save(inUser);
        return responseService.getSuccessResult();
    }
}
