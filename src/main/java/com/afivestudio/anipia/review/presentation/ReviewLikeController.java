package com.afivestudio.anipia.review.presentation;

import com.afivestudio.anipia.review.application.ReviewLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review", description = "レビュー関連のAPIです。")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    // 좋아요 토글 API
    // POST http://localhost:8080/reviews/{reviewId}/likes
    @PostMapping("/{reviewId}/likes")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long userId
    ) {

        boolean isLiked = reviewLikeService.toggleLike(reviewId, userId);

        if (isLiked) {
            return ResponseEntity.ok("いいね！を付けました。");
        } else {
            return ResponseEntity.ok("いいね！を解除しました。");
        }
    }
}
