package com.afivestudio.anipia.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.user")
public record UserProperties(
        String defaultProfileImagePath
) {

}
