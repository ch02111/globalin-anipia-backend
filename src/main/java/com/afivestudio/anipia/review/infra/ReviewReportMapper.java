package com.afivestudio.anipia.review.infra;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewReportMapper {

    // 이미 신고했는지 체크
    boolean existsReport(@Param("reviewId") Long reviewId, @Param("userId") Long userId);

    // 신고 이력 저장
    void insertReport(@Param("reviewId") Long reviewId, @Param("userId") Long userId, @Param("reason") String name);// 이미 신고했는지 체크

}
