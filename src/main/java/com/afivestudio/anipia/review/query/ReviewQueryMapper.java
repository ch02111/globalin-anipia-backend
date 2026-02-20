package com.afivestudio.anipia.review.query;

import com.afivestudio.anipia.review.query.dto.RecentReviewDto;
import com.afivestudio.anipia.review.query.dto.ReviewResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface ReviewQueryMapper {

    // Pageable을 통째로 넘겨서 처리
    List<ReviewResDto> findAllByAnimationId(
            @Param("animationId") Long animationId,
            @Param("pageable") Pageable pageable
    );

    // 전체 개수 카운트
    long countByAnimationId(@Param("animationId") Long animationId);

    // 전체 애니메이션 대상 최신 리뷰 조회
    List<RecentReviewDto> findRecentReviews(@Param("limit") int limit);
}