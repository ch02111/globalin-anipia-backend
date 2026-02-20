package com.afivestudio.anipia.review.query.dto;

import com.afivestudio.anipia.review.domain.ReportReason;
import jakarta.validation.constraints.NotNull;

public record ReviewReportReqDto(
        @NotNull(message = "通報理由を選択してください。")
        ReportReason reason // 프론트에서 "PROFANITY" 처럼 문자열로 보내면 자동 매핑됨
) {
}