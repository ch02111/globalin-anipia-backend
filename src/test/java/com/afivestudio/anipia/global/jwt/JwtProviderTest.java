package com.afivestudio.anipia.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.afivestudio.anipia.config.properties.SecurityProperties;
import com.afivestudio.anipia.user.domain.Profile;
import com.afivestudio.anipia.user.domain.User;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.test.util.ReflectionTestUtils;

class JwtProviderTest {

    // 공통으로 사용할 긴 만료 시간의 Secret Key
    private static final String TEST_SECRET_KEY = "a-very-long-and-secure-secret-key-for-testing-purpose-only";
    private static final String INVALID_SECRET_KEY = "this-is-a-completely-different-and-invalid-secret-key-123";

    @DisplayName("유효한 토큰은 유효성 검증에 성공해야 한다")
    @Test
    void validateToken_SucceedsWhenTokenIsValid() {
        // given: [성공 테스트]를 위한 Provider 생성
        // 충분히 긴 만료 시간(예: 1분)을 가진 Provider를 이 테스트 메서드 내에서만 사용
        SecurityProperties longLivedProperties = new SecurityProperties(null,
                new SecurityProperties.Jwt(
                        TEST_SECRET_KEY,
                        DurationStyle.detectAndParse("1d"),
                        DurationStyle.detectAndParse("1d")
                ), null);
        JwtProvider longLivedJwtProvider = new JwtProvider(longLivedProperties);

        User testUser = createTestUser();
        String accessToken = longLivedJwtProvider.issueAccessToken(testUser);

        // when
        boolean isValid = longLivedJwtProvider.validateToken(accessToken);

        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("만료된 토큰은 유효성 검증에 실패해야 한다")
    @Test
    void validateToken_FailsWhenTokenIsExpired() {
        // given: [실패 테스트]를 위한 Provider 생성
        // 만료 시간이 1ms로 매우 짧은 Provider를 이 테스트 메서드 내에서만 사용
        SecurityProperties shortLivedProperties = new SecurityProperties(null,
                new SecurityProperties.Jwt(
                        TEST_SECRET_KEY,
                        Duration.parse("PT-1S"),
                        Duration.parse("PT-1S")
                ), null);
        JwtProvider shortLivedJwtProvider = new JwtProvider(shortLivedProperties);

        User testUser = createTestUser();
        String expiredToken = shortLivedJwtProvider.issueAccessToken(testUser);

        // when
        boolean isValid = shortLivedJwtProvider.validateToken(expiredToken);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("Secret Key가 다른 토큰은 유효성 검증에 실패해야 한다")
    @Test
    void validateToken_FailsWhenSecretIsDifferent() {
        // given: [토큰 생성용]과 [검증용] Provider를 각각 다른 Secret Key로 생성
        // 1. 토큰 생성용 Provider (Key A 사용)
        SecurityProperties issuerProperties = new SecurityProperties(null,
                new SecurityProperties.Jwt(TEST_SECRET_KEY, Duration.ofMinutes(10), Duration.ofMinutes(10)), null);
        JwtProvider issuerProvider = new JwtProvider(issuerProperties);

        // 2. 토큰 검증용 Provider (Key B 사용)
        SecurityProperties validatorProperties = new SecurityProperties(null,
                new SecurityProperties.Jwt(INVALID_SECRET_KEY, Duration.ofMinutes(10), Duration.ofMinutes(10)), null);
        JwtProvider validatorProvider = new JwtProvider(validatorProperties);

        // 3. Key A로 토큰 생성
        User testUser = createTestUser();
        String tokenSignedWithValidKey = issuerProvider.issueAccessToken(testUser);

        // when: Key B로 토큰 검증 시도
        boolean isValid = validatorProvider.validateToken(tokenSignedWithValidKey);

        // then
        assertThat(isValid).isFalse();
    }

    // 테스트 유저 생성을 위한 헬퍼 메서드
    private User createTestUser() {
        User user = new User("", new Profile("", ""), "");
        ReflectionTestUtils.setField(user, "id", 1L);
        return user;
    }
}
