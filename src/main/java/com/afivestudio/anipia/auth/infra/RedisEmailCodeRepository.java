package com.afivestudio.anipia.auth.infra;

import com.afivestudio.anipia.auth.domain.EmailCodeRepository;
import com.afivestudio.anipia.config.properties.AuthProperties;
import com.afivestudio.anipia.global.redis.RedisKeyType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisEmailCodeRepository implements EmailCodeRepository {

    private final StringRedisTemplate redisTemplate;
    private final AuthProperties authProperties;

    @Override
    public void save(long userId, String authCode) {
        redisTemplate.opsForValue().set(
                RedisKeyType.EMAIL_AUTH.generateKey(Long.toString(userId)),
                authCode,
                authProperties.emailCodeExpiration()
        );
    }

    @Override
    public Optional<String> findOneByUserId(long userId) {
        String authCode = redisTemplate.opsForValue().get(
                RedisKeyType.EMAIL_AUTH.generateKey(Long.toString(userId))
        );
        return Optional.ofNullable(authCode);
    }

    @Override
    public void delete(long userId) {
        redisTemplate.delete(
                RedisKeyType.EMAIL_AUTH.generateKey(Long.toString(userId))
        );
    }
}
