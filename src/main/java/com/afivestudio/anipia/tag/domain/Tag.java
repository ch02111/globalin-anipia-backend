package com.afivestudio.anipia.tag.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {

    private Long id;
    private String text;

    //생성자
    public Tag(String text) {
        this.text = text;
    }

    public void update(String text) {
        this.text = text;
    }
}
