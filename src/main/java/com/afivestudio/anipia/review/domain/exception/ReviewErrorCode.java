package com.afivestudio.anipia.review.domain.exception;

import lombok.Getter;

@Getter
public enum ReviewErrorCode {
    REVIEW_NOT_FOUND("該当するレビューが見つかりません。", 404),
    DUPLICATED_REVIEW("すでにこのアニメーションにレビューを投稿しています。", 409), // 409: Conflict (충돌)
    NOT_MY_REVIEW("自分のレビューのみ修正・削除できます。", 403),    // 403: Forbidden (권한 없음)
    CONTAINS_PROFANITY("不適切な表現（禁止語句）が含まれているため、登録できません。", 400), //비속어 에러코드
    DUPLICATED_REPORT("すでに通報済みのレビューです。", 409); //리뷰 중복신고 에러처리

    private final String message;
    private final int status;

    ReviewErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}