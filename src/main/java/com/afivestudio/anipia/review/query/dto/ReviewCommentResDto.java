package com.afivestudio.anipia.review.query.dto;

import java.time.LocalDateTime;

public record ReviewCommentResDto(
        Long commentId,
        Long reviewId,
        String content,
        LocalDateTime createdAt,
        // 작성자 정보
        Long userId,
        String nickname,
        String profileImagePath
) {
}