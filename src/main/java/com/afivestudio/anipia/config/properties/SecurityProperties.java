package com.afivestudio.anipia.config.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("security")
public record SecurityProperties(
        List<@Valid CustomRequestMatcher> whiteList,
        @Valid Jwt jwt,
        Cors cors
) {

    // 내부 record 정의
    public record CustomRequestMatcher(
            // 3. 정규식으로 HTTP Method 제한 (null은 허용 = null이면 검사 통과)
            @Pattern(regexp = "^(GET|POST|PUT|DELETE|PATCH)$")
            String method,

            @NotBlank
            String pattern
    ) {

        public HttpMethod getMethodOrNull() {
            if (this.method == null) {
                return null;
            }

            return HttpMethod.valueOf(method);
        }
    }

    public record Jwt(
            @NotBlank
            String secretKey,
            Duration accessTokenExpiration,
            Duration refreshTokenExpiration
    ) {

    }

    public record Cors(
            List<String> allowedOrigins
    ) {

    }
}
