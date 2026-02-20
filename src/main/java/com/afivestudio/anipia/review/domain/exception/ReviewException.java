package com.afivestudio.anipia.review.domain.exception;

import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final int status;

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }
}