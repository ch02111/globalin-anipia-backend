package com.afivestudio.anipia.animation.domain;

import com.afivestudio.anipia.company.domain.Company;
import com.afivestudio.anipia.tag.domain.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Animation {

    private Long id;
    private String title;
    private String thumbnailImagePath;
    private String summary;
    private Integer season;
    private LocalDateTime releaseDate;

    // ID 대신 실제 객체를 참조합니다.
    private Company company;
    private List<Tag> tags;

    private long reviewCount;
    private float ratingSum;
    private long bookmarkCount;

    public Animation(Long id, String title, String thumbnailImagePath, String summary, Integer season, LocalDateTime releaseDate, Company company, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.thumbnailImagePath = thumbnailImagePath;
        this.summary = summary;
        this.season = season;
        this.releaseDate = releaseDate;
        this.company = company;
        this.tags = tags;
    }

    /**
     * 정적 팩토리 메서드: 생성 시점에 연관된 객체들을 모두 주입받습니다.
     */
    public static Animation create(String title, String thumbnailImagePath, String summary, Integer season, LocalDateTime releaseDate, Company company, List<Tag> tags) {
        return new Animation(null, title, thumbnailImagePath, summary, season, releaseDate, company, tags);
    }

    public void update(
            String title,
            String thumbnailImagePath,
            String summary,
            Integer season,
            LocalDateTime releaseDate
    ) {
        this.title = title;
        this.thumbnailImagePath = thumbnailImagePath;
        this.summary = summary;
        this.season = season;
        this.releaseDate = releaseDate;
    }
}