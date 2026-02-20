package com.afivestudio.anipia.animation.query.dto;

import java.util.List;

public record CuratedAnimations(
        List<CuratedAnimation> curatedAnimations
) {

    public record CuratedAnimation(
            long animationId,
            String title,
            String imagePath,
            int displayOrder
    ) {

    }
}
