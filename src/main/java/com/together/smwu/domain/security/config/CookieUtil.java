package com.together.smwu.domain.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static void create(HttpServletResponse response, String name, String token,
                              Boolean secure, Integer maxAge, String domain) {
        Cookie cookie = new Cookie(name, token);
        cookie.setSecure(secure); //secure == true : works on HTTPS only
        cookie.setHttpOnly(true); // invisible to Javascript
        cookie.setMaxAge(maxAge); //maxAge == 0 : expire cookie now.
        cookie.setDomain(domain); // visible to domain only
        cookie.setPath("/"); //visible all path
        response.addCookie(cookie);
    }

    /*
    쿠키 삭제
     */
    public static void clear(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    /* 쿠키조회 */
    public static Optional<Cookie> getValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }
}
