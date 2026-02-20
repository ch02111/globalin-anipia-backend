package com.afivestudio.anipia.review.infra;

import com.afivestudio.anipia.review.domain.ReviewLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewLikeRepositoryImpl implements ReviewLikeRepository {

    private final ReviewLikeMapper reviewLikeMapper;

    @Override
    public boolean existsLike(Long reviewId, Long userId) {
        return reviewLikeMapper.existsLike(reviewId, userId);
    }

    @Override
    public void addLike(Long reviewId, Long userId) {
        reviewLikeMapper.insertLike(reviewId, userId);
    }

    @Override
    public void removeLike(Long reviewId, Long userId) {
        reviewLikeMapper.deleteLike(reviewId, userId);
    }
}