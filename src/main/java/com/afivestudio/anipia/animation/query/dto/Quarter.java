package com.afivestudio.anipia.animation.query.dto;

import java.time.LocalDate;
import java.time.YearMonth;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Quarter {
    SPRING(4, 6),
    SUMMER(7, 9),
    FALL(10, 12),
    WINTER(1, 3);

    private final int startMonth;
    private final int endMonth;

    // 연도(year)를 주면 그 분기의 '시작일'을 계산해서 반환
    public LocalDate getStartDate(int year) {
        return LocalDate.of(year, startMonth, 1);
    }

    // 연도(year)를 주면 그 분기의 '마지막 날'을 계산해서 반환
    public LocalDate getEndDate(int year) {
        YearMonth yearMonth = YearMonth.of(year, endMonth);
        return yearMonth.atEndOfMonth();
    }
}
