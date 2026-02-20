package com.afivestudio.anipia.user.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    private Long id;
    private String email;
    private Profile profile;
    private String password;
    private Role role;

    private boolean emailVerified;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public User(String email, Profile profile, String password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
        this.role = Role.ROLE_USER;
        this.emailVerified = false;
    }

    public void updateProfile(Profile newProfile) {
        if (this.profile.equals(newProfile)) {
            return;
        }
        this.profile = newProfile;
    }

    public void updateEmail(String newEmail) {
        if (this.email.equals(newEmail)) {
            return;
        }
        this.email = newEmail;
        this.emailVerified = false;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void verifyEmail() {
        this.emailVerified = true;
    }

    public void withdraw(String defaultProfileImagePath) {
        this.email = null;
        this.profile = new Profile(defaultProfileImagePath, null);
        this.deletedAt = LocalDateTime.now();
    }
}
