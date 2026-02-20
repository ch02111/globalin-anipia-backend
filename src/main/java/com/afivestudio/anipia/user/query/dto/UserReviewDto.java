package com.afivestudio.anipia.user.query.dto;

import java.time.LocalDateTime;

public record UserReviewDto(
        long reviewId,
        long userId,
        long animationId,
        String animationTitle,
        String content,
        float rating,
        boolean isSpoiler,
        long likeCount,
        long reportCount,
        boolean isHidden,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
