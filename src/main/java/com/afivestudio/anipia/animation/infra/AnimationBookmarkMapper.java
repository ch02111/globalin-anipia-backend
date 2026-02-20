package com.afivestudio.anipia.animation.infra;

import com.afivestudio.anipia.animation.domain.AnimationBookmark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AnimationBookmarkMapper {
    public int insert(AnimationBookmark bookmark);

    public int delete(Long id);

    public Optional<AnimationBookmark> findByAnimationIdAndUserId(@Param("animationId") Long animationId, @Param("userId") Long userId);
}
