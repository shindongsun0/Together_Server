package com.together.smwu.domain.user.application.social;

import com.google.gson.Gson;
import com.together.smwu.domain.user.dto.social.KakaoProfile;
import com.together.smwu.domain.user.dto.social.RetKakaoAuth;
import com.together.smwu.global.advice.exception.CCommunicationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class  KakaoService{

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    public KakaoService(@Qualifier("getRestTemplate") RestTemplate restTemplate, Environment env, Gson gson){
        this.restTemplate = restTemplate;
        this.env = env;
        this.gson = gson;
    }
    
    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @Value("${spring.social.kakao.url.logout}")
    private String kakaoLogoutUrl;

    public KakaoProfile getKakaoProfile(String accessToken){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(Objects.requireNonNull(env.getProperty("spring.social.kakao.url.profile")), request, String.class);
            if(response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), KakaoProfile.class);
            }
        } catch(Exception e){
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public RetKakaoAuth getKakaoTokenInfo(String code){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirect);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(Objects.requireNonNull(env.getProperty("spring.social.kakao.url.token")), request, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return gson.fromJson(response.getBody(), RetKakaoAuth.class);
        }
        return null;
    }

    public String kakaoUserLogOut(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + code);
        //set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> responseId = restTemplate.postForEntity(kakaoLogoutUrl, request, String.class);
            return responseId.getBody();
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        }
        return null;
    }

}