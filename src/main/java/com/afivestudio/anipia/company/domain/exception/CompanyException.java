package com.afivestudio.anipia.company.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompanyException extends RuntimeException {

    private final int httpStatus;

    public CompanyException(CompanyErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
    }
}
