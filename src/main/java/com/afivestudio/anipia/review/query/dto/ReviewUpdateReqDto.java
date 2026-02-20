package com.afivestudio.anipia.review.query.dto;

import jakarta.validation.constraints.*;

public record ReviewUpdateReqDto(
        @NotBlank(message = "レビュー内容は必須項目です。")
        @Size(min = 10, max = 1000, message = "レビューは10文字以上1000文字以内で入力してください。")
        String content,

        @NotNull(message = "評価は必須項目です。")
        @Min(1) @Max(5)
        Float rating,

        @NotNull
        Boolean isSpoiler
) {
}