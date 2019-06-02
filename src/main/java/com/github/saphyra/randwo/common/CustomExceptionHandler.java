package com.github.saphyra.randwo.common;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handleEmptyRequestBody() {
        return ResponseEntity.badRequest().body(errorResponse());
    }

    private ErrorResponse errorResponse() {
        return ErrorResponse.builder()
            .httpStatus(HttpStatus.BAD_REQUEST.value())
            .errorCode(ErrorCode.REQUEST_BODY_MISSING.getErrorCode())
            .build();
    }
}
