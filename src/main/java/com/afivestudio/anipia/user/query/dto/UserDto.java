package com.afivestudio.anipia.user.query.dto;

import com.afivestudio.anipia.user.domain.Role;
import java.time.LocalDateTime;

public record UserDto(
        long userId,
        String email,
        String nickname,
        String profileImagePath,
        Role role,
        boolean emailVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {

}
