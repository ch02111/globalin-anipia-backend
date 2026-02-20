package com.afivestudio.anipia.review.domain;

public interface ReviewLikeRepository {
    boolean existsLike(Long reviewId, Long userId);

    void addLike(Long reviewId, Long userId);

    void removeLike(Long reviewId, Long userId);
}