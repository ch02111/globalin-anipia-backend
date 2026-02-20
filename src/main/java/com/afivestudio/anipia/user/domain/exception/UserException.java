package com.afivestudio.anipia.user.domain.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final int status;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }
}
