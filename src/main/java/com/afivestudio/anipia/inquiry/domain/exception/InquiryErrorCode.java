package com.afivestudio.anipia.inquiry.domain.exception;

import lombok.Getter;

@Getter
public enum InquiryErrorCode {

    NOT_FOUND("該当するお問い合わせが見つかりません。", 404);

    private final String message;
    private final int status;

    InquiryErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
