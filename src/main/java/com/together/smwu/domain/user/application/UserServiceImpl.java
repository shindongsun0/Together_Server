package com.together.smwu.domain.user.application;

import com.together.smwu.domain.security.config.CookieUtil;
import com.together.smwu.domain.security.config.jwt.JwtTokenProvider;
import com.together.smwu.domain.user.application.interfaces.UserService;
import com.together.smwu.domain.user.application.social.KakaoService;
import com.together.smwu.domain.user.dao.UserRepository;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.dto.request.SignInRequest;
import com.together.smwu.domain.user.dto.request.SignUpRequest;
import com.together.smwu.domain.user.dto.request.UserRequest;
import com.together.smwu.domain.user.dto.response.LoginResponse;
import com.together.smwu.domain.user.dto.response.UserResponse;
import com.together.smwu.domain.user.dto.social.KakaoProfile;
import com.together.smwu.domain.user.exception.UserNotFoundException;
import com.together.smwu.global.advice.exception.CUserExistException;
import com.together.smwu.global.advice.exception.CUserNotFoundException;
import com.together.smwu.global.aws.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final S3Uploader s3Uploader;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private KakaoService kakaoService;

    private static final String COOKIE_NAME = "X-AUTH-TOKEN";
    private static final Integer TOKEN_VALID_MILLISECOND = 1000 * 60 * 60 * 24;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           KakaoService kakaoService, S3Uploader s3Uploader){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoService = kakaoService;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public LoginResponse login(SignInRequest signInRequest,
                               HttpServletResponse response, String accessToken) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signInRequest.getAccessToken());
        User user = userRepository.findBySocialIdAndProvider(String.valueOf(kakaoProfile.getId()), signInRequest.getProvider())
                .orElseThrow(CUserNotFoundException::new);

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);

        // Valid한 토큰이 아니라면 Cookie에 새로운 토큰을 넣는다.
        if(!accessTokenValid){
            String newJwtToken = jwtTokenProvider.createToken(String.valueOf(user.getUserId()), user.getRoles());
            CookieUtil.create(response, COOKIE_NAME, newJwtToken, true, TOKEN_VALID_MILLISECOND, "*");
        }

        return new LoginResponse(LoginResponse.Status.SUCCESS,
                "Auth successful. Tokens are created in cookie.");
    }

    @Override
    public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {
        return null;
    }

    @Override
    public UserResponse findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.from(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return UserResponse.listFrom(users);
    }

    @Override
    public void updateUser(UserRequest request, User user) {
        user.update(request.toUserEntity());
        userRepository.save(user);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.clear(request, response, COOKIE_NAME);
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(signUpRequest.getAccessToken());
        Optional<User> user = userRepository.findBySocialIdAndProvider(
                String.valueOf(kakaoProfile.getId()), signUpRequest.getProvider());

        if (user.isPresent())
            throw new CUserExistException();

        User inUser = User.builder()
                .socialId(String.valueOf(kakaoProfile.getId()))
                .provider(signUpRequest.getProvider())
                .name(signUpRequest.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        if(null == signUpRequest.getProfileImage()){
            String DEFAULT_USER_IMAGE = "https://together-user-thumbnail.s3.ap-northeast-2.amazonaws.com/static/default_userImage.png";
            inUser.setProfileImage(DEFAULT_USER_IMAGE);
        }else{
            inUser.setProfileImage(signUpRequest.getProfileImage());
        }

        userRepository.save(inUser);
    }

    @Override
    public void updateProfileImage(MultipartFile multipartFile, User user) throws IOException {
        String fileName = "static/" + user.getUserId();
        String url = s3Uploader.upload(multipartFile, fileName);
        user.setProfileImage(url);
        userRepository.save(user);
    }
}
