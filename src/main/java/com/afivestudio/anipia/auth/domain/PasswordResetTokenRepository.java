package com.afivestudio.anipia.auth.domain;

import java.util.Optional;

public interface PasswordResetTokenRepository {

    void save(String email, String token);

    Optional<String> findOneByToken(String token);

    void deleteByToken(String token);

    void deleteByEmail(String email);
}
