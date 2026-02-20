package com.afivestudio.anipia.auth.infra;

import com.afivestudio.anipia.auth.domain.PasswordResetTokenRepository;
import com.afivestudio.anipia.config.properties.AuthProperties;
import com.afivestudio.anipia.global.redis.RedisKeyType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisPasswordResetTokenRepository implements PasswordResetTokenRepository {

    private final StringRedisTemplate redisTemplate;
    private final AuthProperties authProperties;


    @Override
    public void save(String email, String token) {
        // 1. (기존) token -> email 저장
        // Key: "pw-reset-token:{token}", Value: email
        String tokenKey = RedisKeyType.PASSWORD_RESET.generateKey(token);
        redisTemplate.opsForValue().set(tokenKey, email, authProperties.passwordTokenExpiration());

        // 2. (추가) email -> token 저장
        // Key: "pw-reset-email:{email}", Value: token
        String emailKey = RedisKeyType.PASSWORD_RESET.generateKey(email);
        redisTemplate.opsForValue().set(emailKey, token, authProperties.passwordTokenExpiration());
    }

    @Override
    public Optional<String> findOneByToken(String token) {
        String email = redisTemplate.opsForValue().get(
                RedisKeyType.PASSWORD_RESET.generateKey(token)
        );
        return Optional.ofNullable(email);
    }

    @Override
    public void deleteByToken(String token) {
        String email = redisTemplate.opsForValue().get(
                RedisKeyType.PASSWORD_RESET.generateKey(token)
        );
        redisTemplate.delete(
                RedisKeyType.PASSWORD_RESET.generateKey(token)
        );
        redisTemplate.delete(
                RedisKeyType.PASSWORD_RESET.generateKey(email)
        );
    }

    @Override
    public void deleteByEmail(String email) {
        String token = redisTemplate.opsForValue().get(
                RedisKeyType.PASSWORD_RESET.generateKey(email)
        );
        redisTemplate.delete(
                RedisKeyType.PASSWORD_RESET.generateKey(email)
        );
        redisTemplate.delete(
                RedisKeyType.PASSWORD_RESET.generateKey(token)
        );
    }
}
