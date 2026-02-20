package com.afivestudio.anipia.animation.infra;

import com.afivestudio.anipia.animation.domain.Animation;
import com.afivestudio.anipia.animation.domain.AnimationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisAnimationRepository implements AnimationRepository {

    private final AnimationMapper animationMapper;


    @Override
    public Animation save(Animation animation) {
        // 1. MyBatis가 이해할 수 있는 형태(Map)로 짐을 다시 쌉니다.
        Map<String, Object> params = new HashMap<>();

        // XML의 #{...} 이름들과 정확히 일치시켜 줍니다.
        params.put("title", animation.getTitle());
        params.put("thumbnailImagePath", animation.getThumbnailImagePath());
        params.put("summary", animation.getSummary());
        params.put("season", animation.getSeason());
        params.put("reviewCount", animation.getReviewCount());
        params.put("bookmarkCount", animation.getBookmarkCount());
        params.put("ratingSum", animation.getRatingSum());
        params.put("releaseDate", animation.getReleaseDate());

        // 유령 에러의 원인:#{companyName}을 여기서 직접 해결!
        params.put("companyName", animation.getCompany() != null ? animation.getCompany().getName() : null);

        if (animation.getId() == null) {
            // 2. 등록 시도
            animationMapper.insert(params);

            // 3. DB가 만들어준 ID를 꺼내서 엔티티에 몰래 넣어줍니다. (Reflection)
            // 이 작업을 안 하면 response에 ID가 null로 나갑니다.
            Object idObj = params.get("id");
            if (idObj != null) {
                Long generatedId = ((Number) idObj).longValue();
                reflectId(animation, generatedId);
            }
        } else {
            // 4. 수정 시도
            params.put("id", animation.getId());
            animationMapper.update(params);
        }

        return animation;
    }

    private void reflectId(Animation animation, Long id) {
        try {
            java.lang.reflect.Field field = Animation.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(animation, id);
        } catch (Exception e) {
            throw new RuntimeException("ID 주입 중 무서운 에러가 발생했습니다!", e);
        }
    }

    @Override
    public void delete(Long id) {
        animationMapper.delete(id);
    }

    @Override
    public Optional<Animation> findById(Long id) {
        return animationMapper.findById(id);
    }

    @Override
    public void updateRating(Long id, Long reviewCountDelta, float ratingDelta) {
        animationMapper.updateRating(id, reviewCountDelta, ratingDelta);
    }

    @Override
    public void updateBookmarkCount(Long id, int delta) {
        animationMapper.updateBookmarkCount(id, delta);
    }

    //TODO : search 구현
//    @Override
//    public List<Animation> search(AnimationSearchCondition condition, Pageable pageable) {
//        return animationMapper.search(condition, pageable);
//    }
}
