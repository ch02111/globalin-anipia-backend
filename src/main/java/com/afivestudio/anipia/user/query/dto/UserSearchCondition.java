package com.afivestudio.anipia.user.query.dto;

import com.afivestudio.anipia.user.domain.Role;

public record UserSearchCondition(

        String email,
        String nickname,
        Role role,
        Boolean emailVerified,
        Boolean isDeleted
) {

}
