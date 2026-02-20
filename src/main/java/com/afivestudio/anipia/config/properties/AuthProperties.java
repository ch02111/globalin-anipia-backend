package com.afivestudio.anipia.config.properties;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.auth")
public record AuthProperties(
        String emailVerificationUrl,
        Duration emailCodeExpiration,
        String passwordResetUrl,
        Duration passwordTokenExpiration
) {

}
