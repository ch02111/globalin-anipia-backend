package com.afivestudio.anipia.inquiry.query;

import com.afivestudio.anipia.inquiry.query.dto.InquirySearch;
import com.afivestudio.anipia.inquiry.query.dto.InquiryView;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryQueryController {

    private final InquiryQueryMapper mapper;

    @GetMapping
    public PagedModel<InquiryView> search(

            @ParameterObject InquirySearch condition,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable

    ) {
        List<InquiryView> list = mapper.search(condition, pageable);
        long total = mapper.count(condition);

        return new PagedModel<>(
                new PageImpl<>(list, pageable, total)
        );
    }

    @GetMapping("/{id}")
    public InquiryView findById(@PathVariable Long id) {
        return mapper.findById(id);
    }
}