package com.afivestudio.anipia.global;

import com.afivestudio.anipia.auth.domain.exception.AuthException;
import com.afivestudio.anipia.inquiry.domain.exception.InquiryException;
import com.afivestudio.anipia.review.domain.exception.ReviewException;
import com.afivestudio.anipia.tag.domain.exception.TagException;
import com.afivestudio.anipia.user.domain.exception.UserException;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolationException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<CommonErrorResponse> handleUserException(UserException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new CommonErrorResponse(
                        e.getStatus(),
                        e.getMessage(),
                        Collections.emptyList()
                ));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<CommonErrorResponse> handleAuthException(AuthException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new CommonErrorResponse(
                        e.getHttpStatus(),
                        e.getMessage(),
                        Collections.emptyList()
                ));
    }

    @ExceptionHandler(TagException.class)
    public ResponseEntity<CommonErrorResponse> handleTagException(TagException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new CommonErrorResponse(
                        e.getHttpStatus(),
                        e.getMessage(),
                        Collections.emptyList()
                ));
    }

    @ExceptionHandler(InquiryException.class)
    public ResponseEntity<CommonErrorResponse> handleInquiryException(InquiryException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new CommonErrorResponse(
                        e.getStatus(),
                        e.getMessage(),
                        Collections.emptyList()
                ));
    }

    // 리뷰 관련 에러 처리
    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<CommonErrorResponse> handleReviewException(ReviewException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new CommonErrorResponse(
                        e.getStatus(),
                        e.getMessage(),
                        Collections.emptyList()
                ));
    }

    // @Valid 어노테이션 입력값 검증 예외 핸들링
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request
    ) {
        return ResponseEntity
                .badRequest()
                .body(CommonErrorResponse.from(ex.getBindingResult()));
    }

    // @RequestParam 유효성 검사 실패 시 이 메서드가 잡습니다.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(400, e.getMessage(), Collections.emptyList()));
    }

    /**
     * 파라미터 타입 불일치 상황: /api/users/abc (숫자 자리인데 문자 넣음)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .badRequest()
                .body(new CommonErrorResponse(
                        400,
                        "不正なリクエストです。値の型を確認してください。 (필드명: " + ex.getName() + ")",
                        Collections.emptyList()
                ));
    }

    /**
     * 잘못된 경로 요청 (스프링 부트 3.2+ 기준) 상황: /api/users/ (path variable 누락으로 인한 404) 참고: 이 예외를 잡지 않으면 스프링 기본 404 응답이 나갑니다.
     * 커스텀하고 싶을 때만 추가하세요.
     */
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CommonErrorResponse(
                        404,
                        "リクエストされた経路が見つかりません。",
                        Collections.emptyList()
                ));
    }
}
