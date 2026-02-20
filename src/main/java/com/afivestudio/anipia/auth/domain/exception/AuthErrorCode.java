package com.afivestudio.anipia.auth.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthErrorCode {

    LOGIN_FAILED("メールアドレスまたはパスワードが一致しません。", 400),
    INVALID_TOKEN("有効しないトークンです。", 400),
    INVALID_EMAIL_CODE("有効しないメール認証コードです。", 400);

    private final String message;
    private final int httpStatus;
}
