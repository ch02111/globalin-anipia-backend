package com.afivestudio.anipia.global.jwt;

import com.afivestudio.anipia.config.properties.SecurityProperties;
import com.afivestudio.anipia.user.domain.Role;
import com.afivestudio.anipia.user.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final SecurityProperties securityProperties;

    public String issueAccessToken(User user) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withClaim("email_verified", user.isEmailVerified())
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(securityProperties.jwt().accessTokenExpiration().toMillis()))
                .sign(Algorithm.HMAC256(securityProperties.jwt().secretKey()));
    }

    public String issueRefreshToken(User user) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(securityProperties.jwt().refreshTokenExpiration().toMillis()))
                .sign(Algorithm.HMAC256(securityProperties.jwt().secretKey()));
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(securityProperties.jwt().secretKey())).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public long getUserId(String token) {
        return Long.parseLong(JWT.decode(token).getSubject());
    }

    public Role getUserRole(String token) {
        return Role.valueOf(JWT.decode(token).getClaim("role").asString());
    }
}
