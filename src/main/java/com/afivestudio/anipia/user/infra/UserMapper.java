package com.afivestudio.anipia.user.infra;

import com.afivestudio.anipia.user.domain.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
interface UserMapper {

    void insert(User user);

    void update(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
