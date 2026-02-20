package com.afivestudio.anipia.review.presentation;

import com.afivestudio.anipia.review.application.ReviewService;
import com.afivestudio.anipia.review.query.dto.ReviewCreateReqDto;
import com.afivestudio.anipia.review.query.dto.ReviewUpdateReqDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "レビュー関連のAPIです。")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 리뷰 작성 API
    // POST http://localhost:8080/reviews
    @PostMapping
    public ResponseEntity<Long> writeReview(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid ReviewCreateReqDto reqDto
    ) {

        Long reviewId = reviewService.createReview(userId, reqDto);
        return ResponseEntity.ok(reviewId);
    }

    // 2. 스포일러 설정 변경 API
    // PATCH http://localhost:8080/reviews/1/spoiler?isSpoiler=true
    @PatchMapping("/{reviewId}/spoiler")
    public ResponseEntity<String> updateSpoiler(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long userId,
            @RequestParam boolean isSpoiler
    ) {

        reviewService.updateSpoiler(reviewId, userId, isSpoiler);
        return ResponseEntity.ok("ネタバレ設定が変更されました。");
    }

    // 3. 리뷰 수정 API
    // PUT http://localhost:8080/reviews/{reviewId}
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid ReviewUpdateReqDto reqDto
    ) {
        reviewService.updateReview(reviewId, userId, reqDto);
        return ResponseEntity.ok("レビューが修正されました。");
    }

    // 4. 리뷰 삭제 API
    // DELETE http://localhost:8080/reviews/{reviewId}
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long userId
    ) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok("レビューが削除されました。");
    }
}