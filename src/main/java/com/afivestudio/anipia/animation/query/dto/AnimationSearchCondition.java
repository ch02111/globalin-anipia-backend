package com.afivestudio.anipia.animation.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record AnimationSearchCondition(
        @Schema(description = "애니메이션 제목 검색어", example = "귀멸의 칼날")
        String title,

        @Schema(description = "태그 아이디 리스트 (다중 선택)", example = "[1, 2]")
        List<Long> tagIds,

        @Schema(description = "제작사 아이디 리스트 (다중 선택)", example = "[1, 2]")
        List<Long> companyIds,

        @Schema(description = "조회할 평점대 리스트 (예: 3 입력 시 3.0~3.99점대)", example = "3")
        Integer minRatings,

        @Schema(description = "연도 (다중 선택)", example = "[2024, 2025]")
        List<Integer> years,

        @Schema(description = "분기 리스트 (다중 선택)", example = "[SPRING, SUMMER]")
        List<Quarter> quarters

) {

    // =========================================================
    // 핵심: 연도 목록 x 분기 목록 = 모든 날짜 범위 생성
    // =========================================================
    public List<DateRange> getDateRanges() {
        if (years == null || years.isEmpty()) {
            return Collections.emptyList();
        }

        List<DateRange> ranges = new ArrayList<>();

        for (Integer year : years) {
            // A. 분기 선택이 없는 경우 -> 해당 연도 전체 (1월 1일 ~ 12월 31일) 추가
            if (quarters == null || quarters.isEmpty()) {
                ranges.add(new DateRange(
                        LocalDate.of(year, 1, 1),
                        LocalDate.of(year, 12, 31)
                ));
            }
            // B. 분기 선택이 있는 경우 -> (연도 x 분기) 조합 생성
            else {
                for (Quarter quarter : quarters) {
                    ranges.add(new DateRange(
                            quarter.getStartDate(year),
                            quarter.getEndDate(year)
                    ));
                }
            }
        }
        return ranges;
    }

    /**
     * MyBatis의 동적 쿼리(XML)에서 tagIds.size()를 직접 호출할 경우, 컬렉션 타입에 따라 OGNL 표현식 파싱 중 예외가 발생할 수 있습니다. (e.g., 수정 불가능한 리스트에서의
     * UnsupportedOperationException) 이 문제를 회피하기 위해, 서비스 로직에서 안전하게 사이즈를 계산하여 반환하는 Getter 메서드를 별도로 제공합니다.
     */
    public int getTagIdsSize() {
        return (tagIds != null) ? tagIds.size() : 0;
    }
}
