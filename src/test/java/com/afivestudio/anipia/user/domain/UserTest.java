package com.afivestudio.anipia.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UserTest {

    private static User createDefault() {
        return new User(
                "test@gmail.com",
                new Profile(null, "test_nickname"),
                "encoded_mock_password"
        );
    }

    private static User createWithId(Long userId) {
        User user = createDefault();
        ReflectionTestUtils.setField(user, "id", userId);
        return user;
    }

    @DisplayName("유저 생성 시 초기 상태 확인")
    @Test
    void create_user() {
        // given
        String email = "start@gmail.com";
        Profile profile = new Profile("img.jpg", "nickname");
        String encodedPassword = "encoded_pw_1234";

        // when
        User user = new User(email, profile, encodedPassword);

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getProfile()).isEqualTo(profile);
        assertThat(user.getPassword()).isEqualTo(encodedPassword);
        assertThat(user.getRole()).isEqualTo(Role.ROLE_USER);
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
        assertThat(user.getDeletedAt()).isNull();
    }

    @DisplayName("이메일 변경 시: 이메일 값 변경 & 인증 상태 초기화")
    @Test
    void update_email() {
        // given
        String newEmail = "new.test@gmail.com";
        User user = createDefault();

        // when
        user.updateEmail(newEmail);

        // then
        assertThat(user.getEmail()).isEqualTo(newEmail);
        assertThat(user.isEmailVerified()).isFalse();
    }

    @DisplayName("프로필 변경 시: 프로필 값 변경")
    @Test
    void update_profile() {
        // given
        Profile newProfile = new Profile("new_profile_img.png", "new_test_nickname");
        User user = createDefault();

        // when
        user.updateProfile(newProfile);

        // then
        assertThat(user.getProfile()).isEqualTo(newProfile);
    }

    @DisplayName("회원탈퇴 시: 이메일 & 닉네임 값 변경")
    @Test
    void withdraw_user() {
        // given
        long userId = 100L;
        User user = createWithId(userId);

        // when
        user.withdraw("/test/img.png");

        // then
        assertThat(user.getEmail()).isEqualTo("del_100_test@gmail.com");
        assertThat(user.getProfile()).isEqualTo(new Profile("/test/img.png", "del_100_test_nickname"));
    }
}
