package com.afivestudio.anipia.animation.domain.exception;

import lombok.Getter;

@Getter
public class AnimationException extends RuntimeException {

    private final int status;

    public AnimationException(AnimationErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }
}