package com.together.smwu.domain.security.config.jwt;

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

public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String JWT_COOKIE_NAME = "X-AUTH-TOKEN";
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String token = getJwtTokenFromCookie(request);
            if (null != token && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Find JWT Cookie and get JWT token
     *
     * @param request incoming HTTP Request
     * @return JWT, or {@code null} if no token was found
     */
    private String getJwtTokenFromCookie(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (null != httpServletRequest.getCookies()) {
            return Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> cookie.getName().equals(JWT_COOKIE_NAME))
                    .map(Cookie::getValue)
                    .findAny()
                    .orElse(null);
        } else {
            return null;
        }
    }
}
