package com.afivestudio.anipia.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.file")
public record FileProperties(
        S3 s3
) {

    public record S3(
            String bucket
    ) {

    }
}
