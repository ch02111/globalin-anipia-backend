package com.afivestudio.anipia.animation.query;

import com.afivestudio.anipia.animation.query.dto.AnimationDto;
import com.afivestudio.anipia.animation.query.dto.AnimationSearchCondition;
import com.afivestudio.anipia.animation.query.dto.CuratedAnimations;
import com.afivestudio.anipia.animation.query.dto.CuratedAnimations.CuratedAnimation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Animation", description = "애니메이션 관련 API 입니다.")
@RequiredArgsConstructor
@Validated
@RequestMapping("/animations")
@RestController
public class AnimationQueryController {

    private final AnimationQueryMapper mapper;

    // 서치 부분은 이렇게 하는거임
    // 나중에 swagger 설정도 넣을 예정
    // 그리고 얘는 시큐리티 화이트 리스트에 넣어야됨 application.yaml에서 화이트리스트 등록하셈
    @Parameter(
            name = "sort",
            description = "정렬 기준 (형식: 필드명,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"releaseDate", "bookmarkCount", "averageRating"} // 애니메이션 정렬 기준
            ))
    )
    @GetMapping
    @Operation(summary = "애니메이션 검색", description = "이름, 태그, 제작사, 분기, 평점 등을 이용한 다중 선택 검색")
    public PagedModel<AnimationDto> search(
            @AuthenticationPrincipal Long userId,
            @ParameterObject AnimationSearchCondition condition,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        long count = mapper.count(condition);
        if (count == 0) {
            return new PagedModel<>(new PageImpl<>(Collections.emptyList(), pageable, count));
        }

        List<Long> ids = mapper.findIds(condition, pageable);
        List<AnimationDto> content = mapper.findAllByIds(userId, ids);

        PageImpl<AnimationDto> page = new PageImpl<>(content, pageable, count);

        return new PagedModel<>(page);
    }

    @Operation(summary = "큐레이션된 애니메이션 조회", description = "모든 큐레이션된 애니메이션을 조회합니다.")
    @GetMapping("/curated")
    public CuratedAnimations getCuratedAnimations() {
        List<CuratedAnimation> curatedAnimationList = mapper.findAllCuratedAnimations();
        return new CuratedAnimations(curatedAnimationList);
    }

    @GetMapping("/{animationId}")
    public AnimationDto getAnimation(
            @AuthenticationPrincipal Long userId,
            @PathVariable @Positive long animationId
    ) {
        return mapper.findOneById(userId, animationId);
    }
}
