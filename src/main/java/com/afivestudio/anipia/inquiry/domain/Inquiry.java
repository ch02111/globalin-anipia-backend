package com.afivestudio.anipia.inquiry.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


// setter는 지양하자(쓰지말자)
// 밖으로 나가면 안된다
// 얼마나 바깥이냐...
// 서버를 벗어나지 않음
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {

    private Long id;
    private String email;
    private String title;
    private String content;
    private InquiryType inquiryType;
    private boolean processed;
    private LocalDateTime createdAt;

    public Inquiry(
            String email,
            String title,
            String content,
            InquiryType inquiryType
    ) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.inquiryType = inquiryType;
        this.processed = false;
    }

    public void updateStatus(boolean processed) {
        this.processed = processed;
    }
}

