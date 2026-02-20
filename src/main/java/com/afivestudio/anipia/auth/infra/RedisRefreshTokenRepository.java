package com.afivestudio.anipia.auth.infra;

import com.afivestudio.anipia.auth.domain.RefreshTokenRepository;
import com.afivestudio.anipia.config.properties.SecurityProperties;
import com.afivestudio.anipia.global.redis.RedisKeyType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private final StringRedisTemplate redisTemplate;
    private final SecurityProperties securityProperties;

    @Override
    public void save(long userId, String refreshToken) {
        redisTemplate.opsForValue().set(
                RedisKeyType.REFRESH_TOKEN.generateKey(Long.toString(userId)),
                refreshToken,
                securityProperties.jwt().refreshTokenExpiration()
        );
    }

    @Override
    public Optional<String> findOneByUserId(long userId) {
        String refreshToken = redisTemplate.opsForValue().get(
                RedisKeyType.REFRESH_TOKEN.generateKey(Long.toString(userId))
        );
        return Optional.ofNullable(refreshToken);
    }

    @Override
    public void delete(long userId) {
        redisTemplate.delete(
                RedisKeyType.REFRESH_TOKEN.generateKey(Long.toString(userId))
        );
    }
}
