package com.afivestudio.anipia.company.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Company {
    private Long id;
    private String name;

    //생성자
    public Company(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}