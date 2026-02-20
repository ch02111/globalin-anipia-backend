package com.afivestudio.anipia.user.infra;

import static org.assertj.core.api.Assertions.assertThat;

import com.afivestudio.anipia.user.domain.Profile;
import com.afivestudio.anipia.user.domain.User;
import com.afivestudio.anipia.user.domain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MyBatisUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User createDefault() {
        return new User(
                "test@gmail.com",
                new Profile(null, "test_nickname"),
                "encoded_mock_password"
        );
    }

    @Test
    @DisplayName("save: ID가 없으면 insert하고 ID를 부여받는다")
    void save_insert() {
        // given
        User user = createDefault();

        // when
        userRepository.save(user);

        // then
        assertThat(user.getId()).isNotNull();
    }

    @Test
    @DisplayName("save: ID가 있으면 update가 수행된다")
    void save_update() {
        // given (먼저 데이터 하나 저장)
        User user = createDefault();
        String beforeEmail = user.getEmail();
        String beforeNickname = user.getProfile().nickname();
        String defaultProfileImagePath = "/test/img.png";
        userRepository.save(user);

        // when (데이터 수정 후 다시 save)
        user.withdraw(defaultProfileImagePath);
        userRepository.save(user); // update 발생 (ID가 있으므로)

        // then
        assertThat(user.getEmail()).isEqualTo("del_%s_%s".formatted(user.getId(), beforeEmail));
        assertThat(user.getProfile()).isEqualTo(
                new Profile(defaultProfileImagePath, "del_%s_%s".formatted(user.getId(), beforeNickname)));
    }

    @Test
    @DisplayName("findByEmail: 이메일로 유저를 조회한다")
    void findByEmail() {
        // given
        User user = createDefault();
        userRepository.save(user);

        // when
        Optional<User> result = userRepository.findByEmail("test@gmail.com");

        // then
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("existsByNickname: 닉네임 존재 여부를 확인한다")
    void existsByNickname() {
        // given
        User user = createDefault();
        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByNickname("test_nickname");
        boolean notExists = userRepository.existsByNickname("없는닉네임");

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
