package com.together.smwu.security.service.security;

import com.together.smwu.advice.exception.CUserNotFoundException;
import com.together.smwu.config.security.CookieUtil;
import com.together.smwu.config.security.JwtTokenProvider;
import com.together.smwu.security.dto.LoginResponse;
import com.together.smwu.security.entity.User;
import com.together.smwu.security.model.request.SignInRequest;
import com.together.smwu.security.model.social.KakaoProfile;
import com.together.smwu.security.repository.UserJpaRepo;
import com.together.smwu.security.service.social.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService{

    private UserJpaRepo userJpaRepo;
    private JwtTokenProvider jwtTokenProvider;
    private KakaoService kakaoService;

    private static final String COOKIE_NAME = "X-AUTH-TOKEN";
    private static final Integer TOKEN_VALID_MILLISECOND = 1000 * 60 * 60 * 24;

    @Autowired
    public UserServiceImpl(UserJpaRepo userJpaRepo, JwtTokenProvider jwtTokenProvider, KakaoService kakaoService){
        this.userJpaRepo = userJpaRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoService = kakaoService;
    }

    @Override
    public LoginResponse login(SignInRequest signInRequest,
                               HttpServletResponse response, String accessToken) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signInRequest.getAccessToken());
        User user = userJpaRepo.findByUidAndProvider(String.valueOf(kakaoProfile.getId()), signInRequest.getProvider())
                .orElseThrow(CUserNotFoundException::new);

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);

        String newJwtToken;
        if(!accessTokenValid){
            newJwtToken = jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles());
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
}
