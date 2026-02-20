package com.afivestudio.anipia.animation.infra;

import com.afivestudio.anipia.animation.domain.Animation;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface AnimationMapper {

    public int insert(Map<String, Object> params);

    public int update(Map<String, Object> params);

    public int delete(Long id);

    public Optional<Animation> findById(Long id);

    public void updateRating(Long id, Long reviewCountDelta, float ratingDelta);

    public int updateBookmarkCount(Long id, int delta);

    // TODO : search 구현
//    public List<Animation> search(AnimationSearchCondition condition, Pageable pageable);
}
