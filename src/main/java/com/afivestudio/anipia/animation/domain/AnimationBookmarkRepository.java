package com.afivestudio.anipia.animation.domain;


import java.util.Optional;

public interface AnimationBookmarkRepository {
    public AnimationBookmark insert(AnimationBookmark bookmark);

    public void delete(AnimationBookmark bookmark);

    public Optional<AnimationBookmark> findByAnimationIdAndUserId(Long animationId, Long userId);

    public boolean existsByAnimationIdAndUserId(Long animationId, Long userId);
}
