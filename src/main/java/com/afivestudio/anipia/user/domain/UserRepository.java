package com.afivestudio.anipia.user.domain;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
