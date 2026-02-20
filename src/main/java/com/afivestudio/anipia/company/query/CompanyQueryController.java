package com.afivestudio.anipia.company.query;

import com.afivestudio.anipia.company.query.dto.CompanyDto;
import com.afivestudio.anipia.company.query.dto.CompanySearchCondition;
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
@RequestMapping("/companies")
@RestController
public class CompanyQueryController {

    private final CompanyQueryMapper mapper;

    @Parameter(
            name = "sort",
            description = "整列基準 (형식: 필드명,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"companyId", "name"}
            ))
    )
    @Operation(operationId = "searchCompanys", summary = "会社一覧の閲覧", description = "会社一覧の閲覧します。")
    @GetMapping
    public PagedModel<CompanyDto> search(
            @ParameterObject CompanySearchCondition condition,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        List<CompanyDto> content = mapper.search(condition, pageable);
        long count = mapper.count(condition);

        PageImpl<CompanyDto> page = new PageImpl<>(content, pageable, count);

        return new PagedModel<>(page);
    }
}

