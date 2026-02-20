package com.afivestudio.anipia.auth.presentation;

import com.afivestudio.anipia.auth.application.AuthService;
import com.afivestudio.anipia.auth.application.EmailVerificationService;
import com.afivestudio.anipia.auth.application.PasswordResetService;
import com.afivestudio.anipia.auth.application.dto.EmailVerifyDto;
import com.afivestudio.anipia.auth.application.dto.JwtDto;
import com.afivestudio.anipia.auth.application.dto.LoginCommand;
import com.afivestudio.anipia.auth.application.dto.PasswordResetCommand;
import com.afivestudio.anipia.auth.application.dto.PasswordUpdateCommand;
import com.afivestudio.anipia.auth.domain.exception.AuthErrorCode;
import com.afivestudio.anipia.auth.domain.exception.AuthException;
import com.afivestudio.anipia.config.properties.SecurityProperties;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;
    private final PasswordResetService passwordResetService;
    private final SecurityProperties securityProperties;

    @PostMapping("/login")
    public JwtDto login(
            HttpServletResponse response,
            @Valid @RequestBody LoginCommand command
    ) {
        JwtDto dto = authService.login(command);

        // set cookie
        String cookie = CookieUtil.buildRefreshTokenCookie(
                dto.refreshToken(),
                securityProperties.jwt().refreshTokenExpiration()
        );
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);

        return dto;
    }

    @PostMapping("/reissue")
    public JwtDto reissue(HttpServletRequest request, HttpServletResponse response) {
        // 1. 쿠키에서 Refresh Token 추출
        String refreshToken = CookieUtil.getRefreshTokenOrNull(request);

        if (refreshToken == null) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }

        try {
            // 2. 서비스 호출 (성공 시)
            JwtDto dto = authService.reissue(refreshToken);

            // 3. 새 Refresh Token을 쿠키에 설정 (갱신)
            String cookie = CookieUtil.buildRefreshTokenCookie(
                    dto.refreshToken(),
                    securityProperties.jwt().refreshTokenExpiration()
            );
            response.addHeader(HttpHeaders.SET_COOKIE, cookie);

            return dto; // Access Token은 Body로

        } catch (AuthException e) {
            // 4. 실패 시: 쿠키 삭제 로직 ⭐⭐⭐
            // 브라우저에게 "이 쿠키 유통기한 끝났어(0초), 당장 버려"라고 명령
            String emptyCookie = CookieUtil.buildEmptyCookie();
            response.addHeader(HttpHeaders.SET_COOKIE, emptyCookie);

            // 에러 응답 반환
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletResponse response,
            @AuthenticationPrincipal long userId
    ) {
        String emptyCookie = CookieUtil.buildEmptyCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, emptyCookie);
        authService.logout(userId);
    }

    @PostMapping("/send-verification-email")
    public void sendVerificationEmail(@AuthenticationPrincipal long userId) {
        emailVerificationService.sendEmail(userId);
    }

    @PostMapping("/verify-email")
    public void verifyEmail(@Valid @RequestBody EmailVerifyDto dto) {
        emailVerificationService.verifyCode(dto.getUserId(), dto.getAuthCode());
    }

    @Operation(summary = "비밀번호 재설정 메일 발송", description = "이메일 존재 여부와 관계없이 성공 응답을 반환합니다 (보안).")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/password-reset/request")
    public void requestPasswordReset(@RequestBody @Valid PasswordResetCommand command) {
        passwordResetService.sendPasswordResetMail(command.email());
    }

    @Operation(summary = "재설정 토큰 유효성 검증", description = "화면 진입 전 토큰이 만료되었는지 확인합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/password-reset/verify")
    public void verifyResetToken(@RequestParam String token) {
        passwordResetService.verifyResetToken(token);
    }

    @Operation(summary = "비밀번호 변경 수행", description = "토큰 검증 후 비밀번호를 변경하고, 기존 로그인 세션을 만료시킵니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/password-reset/confirm")
    public void confirmPasswordReset(@RequestBody @Valid PasswordUpdateCommand command) {
        passwordResetService.resetPassword(command);
    }
}
