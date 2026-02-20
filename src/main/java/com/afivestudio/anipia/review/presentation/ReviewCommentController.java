package com.afivestudio.anipia.review.presentation;

import com.afivestudio.anipia.review.application.ReviewCommentService;
import com.afivestudio.anipia.review.query.dto.ReviewCommentCreateReqDto;
import com.afivestudio.anipia.review.query.dto.ReviewCommentResDto;
import com.afivestudio.anipia.review.query.dto.ReviewCommentUpdateReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewCommentController {

    private final ReviewCommentService commentService;

    // 1. 댓글 작성
    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<String> createComment(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid ReviewCommentCreateReqDto reqDto
    ) {
        commentService.createComment(reviewId, userId, reqDto);
        return ResponseEntity.ok("コメントが登録されました。");
    }

    // 2. 댓글 목록 조회
    @GetMapping("/{reviewId}/comments")
    public ResponseEntity<List<ReviewCommentResDto>> getComments(
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(commentService.getComments(reviewId));
    }

    // 3. 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid ReviewCommentUpdateReqDto reqDto
    ) {
        commentService.updateComment(commentId, userId, reqDto);
        return ResponseEntity.ok("コメントが修正されました。");
    }

    // 4. 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("コメントが削除されました。");
    }
}