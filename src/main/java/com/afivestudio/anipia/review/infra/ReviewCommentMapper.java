package com.afivestudio.anipia.review.infra;

import com.afivestudio.anipia.review.query.dto.ReviewCommentResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewCommentMapper {

    // 댓글 저장
    void save(@Param("reviewId") Long reviewId,
              @Param("userId") Long userId,
              @Param("content") String content);

    // 댓글 조회 (특정 리뷰에 달린 모든 댓글)
    List<ReviewCommentResDto> findAllByReviewId(@Param("reviewId") Long reviewId);

    // 댓글 수정
    void update(@Param("commentId") Long commentId, @Param("content") String content);

    // 댓글 삭제 (Soft Delete)
    void delete(@Param("commentId") Long commentId);

    // 권한 체크용: 댓글 작성자 ID 조회
    Long findUserIdByCommentId(@Param("commentId") Long commentId);
}