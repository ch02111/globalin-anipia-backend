package com.afivestudio.anipia.auth.presentation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import org.springframework.http.ResponseCookie;

class CookieUtil {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "rt";
    private static final String SAME_SITE_VALUE = "Strict";

    public static String buildRefreshTokenCookie(String refreshToken, Duration maxAge) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .path("/")
                .secure(true)
                .sameSite(SAME_SITE_VALUE)
                .httpOnly(true)
                .maxAge(maxAge)
                .build()
                .toString();
    }

    public static String getRefreshTokenOrNull(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static String buildEmptyCookie() {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "") // 빈 값
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite(SAME_SITE_VALUE)
                .maxAge(0) // 핵심: 수명을 0으로 설정
                .build()
                .toString();
    }
}
