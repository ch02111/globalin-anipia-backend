package com.afivestudio.anipia.inquiry.query.dto;

import com.afivestudio.anipia.inquiry.domain.InquiryType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class InquiryView {

    private final long id;
    private final String email;
    private final String title;
    private final String content;
    private final InquiryType inquiryType;
    private final boolean processed;
    private final LocalDateTime createdAt;
}
