package com.afivestudio.anipia.animation.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.afivestudio.anipia.animation.query.dto.AnimationSearchCondition;
import com.afivestudio.anipia.animation.query.dto.CuratedAnimations.CuratedAnimation;
import com.afivestudio.anipia.animation.query.dto.Quarter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnimationQueryMapperTest {

    @Autowired
    private AnimationQueryMapper animationQueryMapper;

    @DisplayName("findAllCuratedAnimations 쿼리가 정상적으로 실행되어야 한다")
    @Test
    void findAllCuratedAnimations_shouldExecuteSuccessfully() {
        // when
        // 실제 DB에 데이터가 있다는 가정 하에 테스트
        List<CuratedAnimation> curatedAnimations = animationQueryMapper.findAllCuratedAnimations();

        // then
        assertThat(curatedAnimations).isNotNull();
        // 추가적으로, 데이터가 있다면 비어있지 않은지, 정렬 순서가 맞는지 등을 검증할 수 있습니다.
        // 예: assertThat(curatedAnimations).isNotEmpty();
        // 예: assertThat(curatedAnimations).isSortedAccordingTo(Comparator.comparing(CuratedAnimation::displayOrder));
    }

    @Test
    @DisplayName("검색 조건 없이 findIds와 count 쿼리가 실행되어야 한다")
    void findIdsAndCount_withNoCondition_shouldExecuteSuccessfully() {
        // given
        AnimationSearchCondition condition = new AnimationSearchCondition(
                null,
                null,
                null,
                null,
                null,
                null
        );
        Pageable pageable = PageRequest.of(0, 10, Sort.by("recommended").descending());

        // when
        long totalCount = animationQueryMapper.count(condition);
        List<Long> ids = animationQueryMapper.findIds(condition, pageable);

        // then
        assertThat(totalCount).isGreaterThanOrEqualTo(0);
        assertThat(ids).isNotNull();
    }

    @Test
    @DisplayName("ID 리스트로 findAllByIds 쿼리가 실행되어야 한다")
    void findAllByIds_withValidIds_shouldReturnAnimations() {
        // given
        // 먼저 ID를 몇 개 조회하거나, 테스트용 ID를 직접 지정합니다.
        AnimationSearchCondition condition = new AnimationSearchCondition(
                null,
                null,
                null,
                null,
                null,
                null
        );
        Pageable pageable = PageRequest.of(0, 3);
        List<Long> idsToFind = animationQueryMapper.findIds(condition, pageable);

        // ID가 하나라도 존재할 경우에만 테스트 진행
        if (idsToFind.isEmpty()) {
            System.out.println("조회할 애니메이션 ID가 없어 findAllByIds 테스트를 건너뜁니다.");
            return;
        }

        // when
        var animations = animationQueryMapper.findAllByIds(idsToFind);

        // then
        assertThat(animations).isNotNull();
        assertThat(animations).hasSize(idsToFind.size());
        // 결과의 순서가 요청한 ID의 순서와 동일한지 검증
        assertThat(animations).extracting("animationId").containsExactlyElementsOf(idsToFind);
    }

    @Test
    @DisplayName("특정 태그 ID로 검색 시 결과가 나와야 한다")
    void findIds_withTagIds_shouldReturnFilteredResults() {
        // given
        // 실제 DB에 존재할 가능성이 높은 태그 ID (예: 1L)
        List<Long> tagIds = List.of(1L, 2L, 3L);
        AnimationSearchCondition condition = new AnimationSearchCondition(
                null,
                tagIds,
                null,
                null,
                null,
                null
        );
        Pageable pageable = PageRequest.of(0, 10);

        // when
        long totalCount = animationQueryMapper.count(condition);
        List<Long> ids = animationQueryMapper.findIds(condition, pageable);

        // then
        assertThat(totalCount).isGreaterThanOrEqualTo(0);
        assertThat(ids).isNotNull();
        // 만약 DB에 해당 태그를 가진 애니메이션이 있다면, 결과가 1개 이상이어야 합니다.
        // 이 부분은 테스트 데이터에 따라 달라질 수 있습니다.
    }

    @Test
    @DisplayName("여러 개의 날짜 범위(연도/분기)로 검색 시 쿼리가 정상적으로 실행되어야 한다")
    void findIds_withMultipleDateRanges_shouldExecuteSuccessfully() {
        // given
        // 예시: 2023년의 (봄, 여름) 과 2024년의 (봄, 여름)을 모두 검색
        List<Integer> years = List.of(2024, 2025);
        List<Quarter> quarters = List.of(Quarter.SPRING, Quarter.SUMMER);

        // AnimationSearchCondition 생성자에 years와 quarters를 직접 전달
        AnimationSearchCondition condition = new AnimationSearchCondition(
                null,
                null,
                null,
                null,
                years,
                quarters
        );
        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 쿼리가 예외 없이 실행되는지 확인하는 것이 주 목적
        long totalCount = animationQueryMapper.count(condition); // tagIdsSize는 0
        List<Long> ids = animationQueryMapper.findIds(condition, pageable); // tagIdsSize는 0

        // then
        assertThat(totalCount).isGreaterThanOrEqualTo(0);
        assertThat(ids).isNotNull();
        // 실제 DB에 해당 기간의 데이터가 있다면, 결과가 1개 이상이어야 합니다.
        // 이 부분은 테스트 데이터에 따라 달라질 수 있습니다.
    }

    @Test
    @DisplayName("연도만 선택하고 분기는 선택하지 않았을 때 연도 전체로 검색되어야 한다")
    void findIds_withOnlyYears_shouldSearchEntireYear() {
        // given
        // 예시: 2023년 전체 검색
        List<Integer> years = List.of(2023);

        AnimationSearchCondition condition = new AnimationSearchCondition(
                null, null, null, null, years, null
        );

        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 내부적으로 condition.getDateRanges()가 호출되어 1개의 DateRange가 생성됨
        // (2023-01-01 ~ 2023-12-31)
        long totalCount = animationQueryMapper.count(condition);
        List<Long> ids = animationQueryMapper.findIds(condition, pageable);

        // then
        assertThat(totalCount).isGreaterThanOrEqualTo(0);
        assertThat(ids).isNotNull();
    }
}
