package com.afivestudio.anipia.global.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeyType {
    REFRESH_TOKEN("user:%s:rt"),
    EMAIL_AUTH("user:%s:email_code"),
    PASSWORD_RESET("password_reset:%s");

    private final String format;

    // Key 생성 편의 메서드
    public String generateKey(String id) {
        return String.format(format, id);
    }
}
