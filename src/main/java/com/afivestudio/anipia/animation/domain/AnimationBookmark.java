package com.afivestudio.anipia.animation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AnimationBookmark {
    private Long id;
    private Long animationId;
    private Long userId;
    private LocalDateTime createdAt;

    public AnimationBookmark(Long id, Long animationId, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.animationId = animationId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public static AnimationBookmark create(Long animationId, Long userId) {
        return new AnimationBookmark(null, animationId, userId, LocalDateTime.now());
    }
}
