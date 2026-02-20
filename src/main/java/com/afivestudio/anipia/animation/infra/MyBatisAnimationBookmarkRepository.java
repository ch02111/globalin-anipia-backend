package com.afivestudio.anipia.animation.infra;

import com.afivestudio.anipia.animation.domain.AnimationBookmark;
import com.afivestudio.anipia.animation.domain.AnimationBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisAnimationBookmarkRepository implements AnimationBookmarkRepository {
    private final AnimationBookmarkMapper bookmarkMapper;

    @Override
    public AnimationBookmark insert(AnimationBookmark bookmark) {
        bookmarkMapper.insert(bookmark);
        return bookmark;
    }

    @Override
    public void delete(AnimationBookmark bookmark) {
        System.out.println("Deleting bookmark with ID: " + bookmark.getId());
        if (bookmark.getId() != null) {
            bookmarkMapper.delete(bookmark.getId());
        }
    }

    @Override
    public Optional<AnimationBookmark> findByAnimationIdAndUserId(Long animationId, Long userId) {
        return bookmarkMapper.findByAnimationIdAndUserId(animationId, userId);
    }

    @Override
    public boolean existsByAnimationIdAndUserId(Long animationId, Long userId) {
        return findByAnimationIdAndUserId(animationId, userId).isPresent();
    }
}
