package com.afivestudio.anipia.review.query.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewCommentUpdateReqDto(
        @NotBlank(message = "内容を入力してください。")
        @Size(max = 500, message = "コメントは500文字以内で入力してください。")
        String content
) {
}