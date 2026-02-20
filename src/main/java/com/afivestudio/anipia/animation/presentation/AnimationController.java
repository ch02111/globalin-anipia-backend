package com.afivestudio.anipia.animation.presentation;

import com.afivestudio.anipia.animation.application.AnimationCreateReqDto;
import com.afivestudio.anipia.animation.application.AnimationCreateResDto;
import com.afivestudio.anipia.animation.application.AnimationService;
import com.afivestudio.anipia.animation.application.AnimationUpdateReqDto;
import com.afivestudio.anipia.animation.domain.exception.AnimationErrorCode;
import com.afivestudio.anipia.animation.domain.exception.AnimationException;
import com.afivestudio.anipia.global.CommonErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Animation", description = "애니메이션 관련 API 입니다.")
@Validated
@RequiredArgsConstructor
@RequestMapping("/animations")
@RestController

public class AnimationController {
    private final AnimationService animationService;

    @Operation(summary = "애니메이션 등록", description = "새로운 애니메이션 정보를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "입력 데이터 유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = CommonErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @ResponseStatus(HttpStatus.CREATED) // 200 대신 201 Created 반환이 표준입니다.
    @PostMapping
    public AnimationCreateResDto create(@RequestBody AnimationCreateReqDto dto) {
        return animationService.create(dto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 애니메이션을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    @Operation(operationId = "updateAnimation", summary = "애니메이션 수정", description = "기존 애니메이션 정보를 수정합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 수정 성공 시 204를 반환
    @PutMapping("/{id}")
    public void update(
            @PathVariable("id") Long id,
            @Valid @RequestBody AnimationUpdateReqDto dto
    ) {
        animationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        animationService.delete(id);
    }

    @PostMapping("/{animationId}/bookmarks")
    public void toggleBookmark(@PathVariable("animationId") Long animationId, @AuthenticationPrincipal Long userId) {
        System.out.println("로그인한 유저 ID 확인: " + userId);
        System.out.println("북마크 토글 애니메이션 ID 확인: " + animationId);
        if (userId == null) {
            throw new AnimationException(AnimationErrorCode.UNAUTHORIZED);
        }
        if (animationId == null) {
            throw new AnimationException(AnimationErrorCode.NOT_FOUND);
        }
        animationService.toggleBookmark(animationId, userId);
    }
}
