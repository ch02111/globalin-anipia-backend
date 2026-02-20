package com.afivestudio.anipia.auth.domain.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final int httpStatus;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}
