package com.together.smwu.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        List<Cookie> cookies = getJwtTokenFromCookie(request);
        String token = jwtTokenProvider.resolveTokenValue(cookies, (HttpServletRequest) request);
        if (null != token && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain. doFilter(request, response);
    }

    private List<Cookie> getJwtTokenFromCookie(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (null != httpServletRequest.getCookies()) {
            return Arrays.asList(httpServletRequest.getCookies());
        } else {
            return Collections.emptyList();
        }
    }
}
