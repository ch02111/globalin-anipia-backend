package com.afivestudio.anipia.user.domain.exception;

import lombok.Getter;

@Getter
public enum UserErrorCode {
    DUPLICATED_EMAIL("すでに存在するメールアドレスです。", 400),
    DUPLICATED_NICKNAME("すでに存在するニックネームです。", 400),
    NOT_FOUND("該当するユーザーが見つかりません。", 400),
    PASSWORD_REUSED("パスワードは再利用できません。", 400),
    FORBIDDEN("アクセス権限がありません。", 403);

    private final String message;
    private final int status;

    UserErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
