package com.afivestudio.anipia.company.domain.exception;

import lombok.Getter;

@Getter
public enum CompanyErrorCode {

    NOT_FOUND("該当する会社を見つかりません", 400);

    private final String message;
    private final int status;

    CompanyErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
