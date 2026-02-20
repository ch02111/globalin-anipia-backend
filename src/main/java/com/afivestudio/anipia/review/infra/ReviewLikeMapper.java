package com.afivestudio.anipia.review.infra;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewLikeMapper {
    // 좋아요 여부 확인
    boolean existsLike(@Param("reviewId") Long reviewId, @Param("userId") Long userId);

    // 좋아요 추가
    void insertLike(@Param("reviewId") Long reviewId, @Param("userId") Long userId);

    // 좋아요 취소
    void deleteLike(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
}