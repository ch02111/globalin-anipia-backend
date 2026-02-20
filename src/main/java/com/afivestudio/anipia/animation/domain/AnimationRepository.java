package com.afivestudio.anipia.animation.domain;

import java.util.Optional;

public interface AnimationRepository {
    public Animation save(Animation animation);


    public void delete(Long id);

    public Optional<Animation> findById(Long id);

    public void updateRating(Long id, Long reviewCountDelta, float ratingDelta);

    public void updateBookmarkCount(Long id, int delta);

    //TODO : search 구현
//    public List<Animation> search(AnimationSearchCondition condition, Pageable pageable);
}
