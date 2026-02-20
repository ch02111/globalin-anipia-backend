package com.afivestudio.anipia.review.query;

import com.afivestudio.anipia.review.query.dto.RecentReviewDto;
import com.afivestudio.anipia.review.query.dto.ReviewResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review", description = "レビュー照会関連API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewQueryController {

    private final ReviewQueryMapper reviewQueryMapper;

    @Parameter(
            name = "sort",
            description = "並び替え基準（デフォルト：新着順）",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"createdAt", "rating", "likeCount"},
                    example = "rating,desc"
            ))
    )
    @Operation(summary = "アニメーション別レビュー一覧取得", description = "特定のアニメーションのレビューをページングして取得します。")
    @GetMapping("/animations/{animationId}")
    public PagedModel<ReviewResDto> getReviews(
            @PathVariable Long animationId,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        // 1. QueryMapper 호출
        List<ReviewResDto> list = reviewQueryMapper.findAllByAnimationId(animationId, pageable);

        // 2. Count 호출
        long count = reviewQueryMapper.countByAnimationId(animationId);

        // 3. PagedModel 변환
        return new PagedModel<>(new PageImpl<>(list, pageable, count));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentReviewDto>> getRecentReviews(
            @RequestParam(defaultValue = "5") int limit // 기본 5개 가져오기
    ) {
        return ResponseEntity.ok(reviewQueryMapper.findRecentReviews(limit));
    }
}