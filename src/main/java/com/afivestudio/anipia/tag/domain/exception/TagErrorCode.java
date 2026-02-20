package com.afivestudio.anipia.tag.domain.exception;

import lombok.Getter;

@Getter
public enum TagErrorCode {

    NOT_FOUND("該当するタグが見つかりません。", 400);

    private final String message;
    private final int status;

    TagErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
