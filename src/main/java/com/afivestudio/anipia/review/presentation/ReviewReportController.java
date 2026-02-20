package com.afivestudio.anipia.review.presentation;

import com.afivestudio.anipia.review.application.ReviewReportService;
import com.afivestudio.anipia.review.query.dto.ReviewReportReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewReportController {

    private final ReviewReportService reportService;

    // 신고하기 API
    @PostMapping("/{reviewId}/reports")
    public ResponseEntity<String> reportReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid ReviewReportReqDto reqDto
    ) {
        reportService.reportReview(reviewId, userId, reqDto.reason());
        return ResponseEntity.ok("レビューが通報されました。");
    }
}