package com.afivestudio.anipia.animation.domain.exception;

import lombok.Getter;

@Getter
public enum AnimationErrorCode {
    // 로그인이 필요한 경우 (401 Unauthorized)
    UNAUTHORIZED("ログインが必要なサービスです", 401),

    // 애니메이션을 찾을 수 없는 경우 (404 Not Found)
    NOT_FOUND("該当するアニメーションが見つかりません。", 404);

    private final String message;
    private final int status;

    AnimationErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}