package com.afivestudio.anipia.animation.query.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimationDto {

    private long animationId;
    private String title;
    private String imagePath;
    private String companyName;
    private String summary;
    private Integer season;
    private boolean isBookmarked;
    private long bookmarkCount;
    private long reviewCount;
    private float averageRating;
    private List<String> tagNames;
}
