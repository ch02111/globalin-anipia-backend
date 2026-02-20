package com.afivestudio.anipia.review.infra;

import com.afivestudio.anipia.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {
    // 리뷰 저장
    void save(Review review);


    // [좋아요] 리뷰 테이블의 like_count 숫자 변경 (+1 또는 -1)
    void updateLikeCount(@Param("reviewId") Long reviewId, @Param("delta") int delta);

    // [스포일러] 스포일러 상태 변경
    void updateSpoilerStatus(@Param("reviewId") Long reviewId, @Param("isSpoiler") boolean isSpoiler);

    //  수정
    void update(Review review);

    // 리뷰 삭제
    void delete(@Param("reviewId") Long reviewId);

    // 리뷰 중복 체크
    boolean existsByAnimationIdAndUserId(@Param("animationId") Long animationId, @Param("userId") Long userId);

    // 리뷰 단건 조회 (수정/삭제 시 검증용)
    Review findById(@Param("reviewId") Long reviewId);

    // 신고 횟수 증가 및 블라인드 처리
    void increaseReportCount(@Param("reviewId") Long reviewId);
}
