package com.afivestudio.anipia.inquiry.presentation;

import com.afivestudio.anipia.inquiry.application.InquiryService;
import com.afivestudio.anipia.inquiry.application.dto.InquiryCreateReqDto;
import com.afivestudio.anipia.inquiry.application.dto.InquiryCreateResDto;
import com.afivestudio.anipia.inquiry.application.dto.InquiryUpdateReqDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public InquiryCreateResDto create(@RequestBody @Valid InquiryCreateReqDto dto) {
        return inquiryService.create(dto);
    }

    @PatchMapping("/{id}")
    public void update(
            @PathVariable @Positive long id,
            @RequestBody @Valid InquiryUpdateReqDto dto
    ) {
        inquiryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        inquiryService.delete(id);
    }
}
