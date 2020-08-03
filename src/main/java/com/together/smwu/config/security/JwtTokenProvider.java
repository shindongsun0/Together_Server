package com.together.smwu.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public static final int COOKIE_LIFE_DURATION = 86400;

    private static final String ACCESS_TOKEN_NAME = "X-AUTH-TOKEN";

    @Value("spring.jwt.secret")
    private String secretKey;

    private long tokenValidMillisecond = 1000L * 60 * 60 * 24;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT Token 생성
    public String createToken(String userPk, List<String> roles) {
//        if (null == userPk) {
//            throw new IllegalAccessException();
//        }
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setClaims(claims) //data
                .setIssuedAt(now) //token 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .compact();
    }

    //Jwt 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
//    public String resolveToken(HttpServletRequest req) {
//        return req.getHeader("X-AUTH-TOKEN");
//    }

    public String resolveTokenValue(List<Cookie> cookies, HttpServletRequest request) {
        List<Cookie> tokenCookieList = getCookiesWithToken(cookies);
        if (!tokenCookieList.isEmpty()) {
            return tokenCookieList.iterator().next().getValue();
        }
        return null;
    }

    private List<Cookie> getCookiesWithToken(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("X-AUTH-TOKEN")) {
                return cookies;
            }
        }
        //cookie에 X-AUTH-TOKEN 이 존재하지 않다면 빈 리스트 반환
        return Collections.emptyList();
    }

    //Jwt 토큰의 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
