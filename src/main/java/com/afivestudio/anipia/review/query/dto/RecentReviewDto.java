package com.afivestudio.anipia.review.query.dto;

import java.time.LocalDateTime;

public record RecentReviewDto(
        // 리뷰 정보
        Long reviewId,
        String content,
        float rating,
        LocalDateTime createdAt,

        // 애니메이션 정보
        Long animationId,
        String animationTitle,
        String animationImagePath
) {
}