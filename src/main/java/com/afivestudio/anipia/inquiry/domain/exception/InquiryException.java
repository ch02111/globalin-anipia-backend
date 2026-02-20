package com.afivestudio.anipia.inquiry.domain.exception;

import lombok.Getter;

@Getter
public class InquiryException extends RuntimeException {

    private final int status;

    public InquiryException(InquiryErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }
}
