package com.afivestudio.anipia.user.infra;

import com.afivestudio.anipia.user.domain.User;
import com.afivestudio.anipia.user.domain.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MyBatisUserRepository implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return userMapper.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userMapper.existsByNickname(nickname);
    }
}
