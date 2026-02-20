package com.afivestudio.anipia.auth.domain;

import java.util.Optional;

public interface EmailCodeRepository {

    void save(long userId, String authCode);

    Optional<String> findOneByUserId(long userId);

    void delete(long userId);
}
