package com.afivestudio.anipia.review.infra;

import com.afivestudio.anipia.review.domain.Review;
import com.afivestudio.anipia.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewMapper reviewMapper;

    @Override
    public Review save(Review review) {
        reviewMapper.save(review);
        return review;
    }

    @Override
    public void updateLikeCount(Long reviewId, int delta) {
        reviewMapper.updateLikeCount(reviewId, delta);
    }

    @Override
    public void updateSpoilerStatus(Long reviewId, boolean isSpoiler) {
        reviewMapper.updateSpoilerStatus(reviewId, isSpoiler);
    }

    @Override
    public void update(Review review) {
        reviewMapper.update(review);
    }

    @Override
    public void delete(Long reviewId) {
        reviewMapper.delete(reviewId);
    }

    @Override
    public boolean existsByAnimationIdAndUserId(Long animationId, Long userId) {
        return reviewMapper.existsByAnimationIdAndUserId(animationId, userId);
    }

    @Override
    public Review findById(Long reviewId) {
        return reviewMapper.findById(reviewId);
    }

}