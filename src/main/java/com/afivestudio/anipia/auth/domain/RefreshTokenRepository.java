package com.afivestudio.anipia.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(long userId, String refreshToken);

    Optional<String> findOneByUserId(long userId);

    void delete(long userId);
}
