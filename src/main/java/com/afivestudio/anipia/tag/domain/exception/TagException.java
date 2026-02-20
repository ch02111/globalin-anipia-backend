package com.afivestudio.anipia.tag.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TagException extends RuntimeException {

    private final int httpStatus;

    public TagException(TagErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
    }
}
