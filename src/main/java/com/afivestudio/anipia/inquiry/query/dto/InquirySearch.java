package com.afivestudio.anipia.inquiry.query.dto;

import com.afivestudio.anipia.inquiry.domain.InquiryType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InquirySearch {
    private final String title;
    private final String content;
    private final InquiryType inquiryType;
    private final Boolean isProcessed;
}
