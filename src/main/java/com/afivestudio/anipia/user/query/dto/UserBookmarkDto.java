package com.afivestudio.anipia.user.query.dto;

public record UserBookmarkDto(
        long animationId,
        String thumbnailImagePath,
        String title,
        float rating
) {

}
