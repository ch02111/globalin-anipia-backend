package com.afivestudio.anipia.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Long reviewId;      // PK
    private Long animationId;   // 애니메이션 FK
    private Long userId;        // 작성자 FK
    private float rating;      // 평점 (1~5)
    private String content;     // 내용
    private Long likeCount;     // 좋아요 수
    private Boolean isSpoiler;  // 스포일러 여부
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reportCount; //신고 카운트
    private Boolean isHidden; //블라인드 여부
}