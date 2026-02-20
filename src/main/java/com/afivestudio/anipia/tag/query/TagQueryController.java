package com.afivestudio.anipia.tag.query;

import com.afivestudio.anipia.tag.query.dto.TagDto;
import com.afivestudio.anipia.tag.query.dto.TagSearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RequestMapping("/tags")
@RestController
public class TagQueryController {

    private final TagQueryMapper mapper;

    @Parameter(
            name = "sort",
            description = "並び替え基準（形式：フィールド名,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"tagId", "text"}
            ))
    )
    @Operation(operationId = "searchTags", summary = "タグ一覧取得", description = "タグの一覧を取得します。")
    @GetMapping
    public PagedModel<TagDto> search(
            @ParameterObject TagSearchCondition condition,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        List<TagDto> content = mapper.search(condition, pageable);
        long count = mapper.count(condition);

        PageImpl<TagDto> page = new PageImpl<>(content, pageable, count);

        return new PagedModel<>(page);
    }
}
