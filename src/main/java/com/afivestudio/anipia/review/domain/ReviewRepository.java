package com.afivestudio.anipia.review.domain;

public interface ReviewRepository {
    // 1. 저장
    Review save(Review review);

    void updateLikeCount(Long reviewId, int delta);

    // 4. 스포일러 업데이트
    void updateSpoilerStatus(Long reviewId, boolean isSpoiler);

    // 5. 수정 & 삭제 (추가 기능)
    void update(Review review);

    void delete(Long reviewId);

    // 6. 중복 체크
    boolean existsByAnimationIdAndUserId(Long animationId, Long userId);

    Review findById(Long reviewId); // 없으면 null 반환하거나 Optional
}