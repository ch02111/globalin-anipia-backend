package com.afivestudio.anipia.config.security;

import com.afivestudio.anipia.config.properties.SecurityProperties;
import com.afivestudio.anipia.config.properties.SecurityProperties.CustomRequestMatcher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final SecurityProperties securityProperties;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/actuator/**")
                .requestMatchers("/api/actuator/**") // 혹시 몰라 둘 다 추가
                .requestMatchers("/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .headers(headers -> headers
                        .xssProtection(xss -> xss
                                .headerValue(HeaderValue.ENABLED))
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("script-src 'self'")
                        )
                )

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/actuator/**").permitAll();
                    auth.requestMatchers("/api/actuator/**").permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    // YAML에 정의된 리스트를 루프 돌면서 설정 적용
                    for (CustomRequestMatcher matcher : securityProperties.whiteList()) {
                        if (matcher.getMethodOrNull() != null) {
                            // 1. 특정 Method가 명시된 경우 (예: GET /users/{id})
                            auth.requestMatchers(
                                    matcher.getMethodOrNull(), // 문자열을 Enum으로 변환
                                    matcher.pattern()
                            ).permitAll();

                        } else {
                            // 2. Method 없이 URL만 있는 경우 (모든 Method 허용)
                            auth.requestMatchers(matcher.pattern()).permitAll();
                        }
                    }

                    // 나머지 요청은 인증 필요
                    auth.anyRequest().authenticated();
                })

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(securityProperties.cors().allowedOrigins());

        // cors 허용 헤더
        configuration.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE
        ));

        // cors 허용 메소드
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.OPTIONS.name()
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
