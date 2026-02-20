package com.afivestudio.anipia.user.query.dto;

public record MeDto(
        UserDto userInfo,
        long totalReviewCount,
        long totalLikeCount
) {

}
