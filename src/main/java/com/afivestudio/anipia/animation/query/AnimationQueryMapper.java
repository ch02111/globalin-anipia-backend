package com.afivestudio.anipia.animation.query;

import com.afivestudio.anipia.animation.query.dto.AnimationDto;
import com.afivestudio.anipia.animation.query.dto.AnimationSearchCondition;
import com.afivestudio.anipia.animation.query.dto.CuratedAnimations.CuratedAnimation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface AnimationQueryMapper {

    //검색 조건에 맞는 애니메이션 아이디만 페이징해서 조회
    List<Long> findIds(
            @Param("condition") AnimationSearchCondition condition,
            @Param("pageable") Pageable pageable
    );

    List<AnimationDto> findAllByIds(Long userId, @Param("ids") List<Long> ids);

    //검색 조건에 맞는 전체 데이터 개수 조회(페이징 처리 위해)
    long count(@Param("condition") AnimationSearchCondition condition);

    List<CuratedAnimation> findAllCuratedAnimations();

    AnimationDto findOneById(Long userId, long animationId);
}
