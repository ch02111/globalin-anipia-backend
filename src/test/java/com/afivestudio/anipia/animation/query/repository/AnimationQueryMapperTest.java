package com.afivestudio.anipia.animation.query.repository;

import com.afivestudio.anipia.animation.query.AnimationQueryMapper;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실 DB 혹은 설정된 H2 사용
class AnimationQueryMapperTest {

    @Autowired
    private AnimationQueryMapper animationQueryMapper;

//    @Test
//    @DisplayName("제목으로 애니메이션을 검색한다.")
//    void searchByTitle() {
//        // given
//        AnimationSearchCondition condition = new AnimationSearchCondition(
//                "귀멸", null, null, null, null, "newest"
//        );
//        Pageable pageable = PageRequest.of(0, 20);
//
//        // when
//        List<AnimationDto> result = animationQueryMapper.search(condition, pageable);
//
//        // then
//        assertThat(result).allMatch(dto -> dto.title().contains("귀멸"));
//    }
//
//    @Test
//    @DisplayName("여러 개의 태그를 모두 포함하는(AND) 애니메이션을 검색한다.")
//    void searchByMultipleTags() {
//        // given
//        List<String> tags = List.of("액션", "판타지");
//        AnimationSearchCondition condition = new AnimationSearchCondition(
//                null, tags, null, null, null, "newest"
//        );
//        Pageable pageable = PageRequest.of(0, 20);
//
//        // when
//        List<AnimationDto> result = animationQueryMapper.search(condition, pageable);
//
//        // then
//        // 모든 결과가 "액션"과 "판타지" 태그를 모두 가지고 있는지 검증
//        for (AnimationDto dto : result) {
//            assertThat(dto.tags()).containsAll(tags);
//        }
//    }
//
//    @Test
//    @DisplayName("특정 분기와 평점대 조건을 만족하는 결과를 조회한다.")
//    void searchByQuarterAndRating() {
//        // given
//        List<Integer> quarters = List.of(1, 2); // 봄, 여름
//        List<Integer> ratings = List.of(4);    // 4점대 (4.0 ~ 4.99)
//        AnimationSearchCondition condition = new AnimationSearchCondition(
//                null, null, null, quarters, ratings, "recommended"
//        );
//        Pageable pageable = PageRequest.of(0, 20);
//
//        // when
//        List<AnimationDto> result = animationQueryMapper.search(condition, pageable);
//
//        // then
//        assertThat(result).allMatch(dto -> dto.averageRating() >= 4.0 && dto.averageRating() < 5.0);
//    }
}
