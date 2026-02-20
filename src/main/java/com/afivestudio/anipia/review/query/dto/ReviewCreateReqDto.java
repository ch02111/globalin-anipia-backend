package com.afivestudio.anipia.review.query.dto;

import com.afivestudio.anipia.review.domain.Review;
import jakarta.validation.constraints.*;

public record ReviewCreateReqDto(
        @NotNull(message = "アニメーションIDは必須項目です。")
        @Positive(message = "正しくないアニメーションIDです。")
        Long animationId,

        @NotBlank(message = "レビュー内容は必須項目です。")
        @Size(min = 10, max = 1000, message = "レビューは10文字以上1000文字以内で入力してください。")
        String content,

        @NotNull(message = "評価は必須項目です。")
        @Min(value = 1, message = "評価は1点以上である必要があります。")
        @Max(value = 5, message = "評価は5点以下である必要があります。")
        Float rating,

        @NotNull(message = "ネタバレ有無を選択してください。") // Boolean 객체일 경우 필수
        Boolean isSpoiler
) {
    public Review toEntity(Long userId) {
        return Review.builder()
                .animationId(this.animationId)
                .userId(userId)
                .rating(this.rating)
                .content(this.content)
                .isSpoiler(this.isSpoiler != null ? this.isSpoiler : false)
                .likeCount(0L)
                .build();
    }
}