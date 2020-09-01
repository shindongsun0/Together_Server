package com.together.smwu.security.service;

import com.together.smwu.advice.exception.CUserExistException;
import com.together.smwu.advice.exception.CUserNotFoundException;
import com.together.smwu.config.S3Uploader;
import com.together.smwu.security.config.CookieUtil;
import com.together.smwu.security.config.jwt.JwtTokenProvider;
import com.together.smwu.security.entity.User;
import com.together.smwu.security.model.request.SignInRequest;
import com.together.smwu.security.model.request.SignUpRequest;
import com.together.smwu.security.model.response.LoginResponse;
import com.together.smwu.security.model.social.KakaoProfile;
import com.together.smwu.security.repository.UserJpaRepo;
import com.together.smwu.security.service.interfaces.UserService;
import com.together.smwu.security.service.social.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final S3Uploader s3Uploader;
    private UserJpaRepo userJpaRepo;
    private JwtTokenProvider jwtTokenProvider;
    private KakaoService kakaoService;

    private static final String COOKIE_NAME = "X-AUTH-TOKEN";
    private static final Integer TOKEN_VALID_MILLISECOND = 1000 * 60 * 60 * 24;

    @Autowired
    public UserServiceImpl(UserJpaRepo userJpaRepo, JwtTokenProvider jwtTokenProvider,
                           KakaoService kakaoService, S3Uploader s3Uploader){
        this.userJpaRepo = userJpaRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoService = kakaoService;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public LoginResponse login(SignInRequest signInRequest,
                               HttpServletResponse response, String accessToken) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signInRequest.getAccessToken());
        User user = userJpaRepo.findByUidAndProvider(String.valueOf(kakaoProfile.getId()), signInRequest.getProvider())
                .orElseThrow(CUserNotFoundException::new);

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);

        // Valid한 토큰이 아니라면 Cookie에 새로운 토큰을 넣는다.
        if(!accessTokenValid){
            String newJwtToken = jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles());
            CookieUtil.create(response, COOKIE_NAME, newJwtToken, false, TOKEN_VALID_MILLISECOND, "*");
        }

        return new LoginResponse(LoginResponse.Status.SUCCESS,
                "Auth successful. Tokens are created in cookie.");
    }

    @Override
    public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {
        return null;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.clear(request, response, COOKIE_NAME);
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signUpRequest.getAccessToken());
        Optional<User> user = userJpaRepo.findByUidAndProvider(
                String.valueOf(kakaoProfile.getId()), signUpRequest.getProvider());

        if (user.isPresent())
            throw new CUserExistException();

        User inUser = User.builder()
                .uid(String.valueOf(kakaoProfile.getId()))
                .provider(signUpRequest.getProvider())
                .name(signUpRequest.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        if(signUpRequest.getProfileImage().isEmpty()){
            String DEFAULT_USER_IMAGE = "https://together-user-thumbnail.s3.ap-northeast-2.amazonaws.com/static/default_userImage.png";
            inUser.setProfileImage(DEFAULT_USER_IMAGE);
        }else{
            inUser.setProfileImage(signUpRequest.getProfileImage());
        }

        userJpaRepo.save(inUser);
    }
}
