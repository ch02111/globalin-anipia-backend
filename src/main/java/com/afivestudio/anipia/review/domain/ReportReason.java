package com.afivestudio.anipia.review.domain;

public enum ReportReason {
    PROFANITY("不適切な表現（暴言・誹謗中傷）"),
    SPOILER("ネタバレを含む"),
    ADVERTISEMENT("広告・宣伝目的の投稿"),
    IRRELEVANT("テーマと無関係な内容");

    private final String description;

    ReportReason(String description) {
        this.description = description;
    }
}