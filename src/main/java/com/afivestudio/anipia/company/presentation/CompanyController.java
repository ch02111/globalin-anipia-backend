package com.afivestudio.anipia.company.presentation;

import com.afivestudio.anipia.company.application.CompanyService;
import com.afivestudio.anipia.company.application.dto.CompanyCreateDto;
import com.afivestudio.anipia.company.application.dto.CompanyIdDto;
import com.afivestudio.anipia.company.application.dto.CompanyUpdateDto;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RequestMapping("/companies")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public CompanyIdDto createCompany(@RequestBody CompanyCreateDto dto) {
        long id = companyService.create(dto);
        return new CompanyIdDto(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateCompany(
            @PathVariable @Positive long id,
            @RequestBody CompanyUpdateDto request
    ) {
        companyService.update(id, request.getName());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable @Positive long id) {
        companyService.delete(id);
    }
}
