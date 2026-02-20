package com.afivestudio.anipia.global;

import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public record CommonErrorResponse(
        int status,
        String message,
        List<CustomFieldError> errors
) {

    public static CommonErrorResponse from(BindingResult bindingResult) {
        return new CommonErrorResponse(
                400, // 어차피 400 고정
                "잘못된 요청입니다.",
                bindingResult.getFieldErrors().stream()
                        .map(CustomFieldError::new)
                        .toList()
        );
    }

    record CustomFieldError(
            String field,
            String value,
            String reason
    ) {

        public CustomFieldError(FieldError fieldError) {
            this(
                    fieldError.getField(),
                    fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage()
            );
        }
    }
}
