package com.afivestudio.anipia.review.query.dto;

import java.time.LocalDateTime;

public record ReviewResDto(
        Long reviewId,
        String content,
        Float rating,
        Long likeCount,
        Boolean isSpoiler,
        Boolean isHidden,
        LocalDateTime createdAt,
        // 작성자 정보
        Long userId,
        String nickname,
        String profileImagePath
) {
}