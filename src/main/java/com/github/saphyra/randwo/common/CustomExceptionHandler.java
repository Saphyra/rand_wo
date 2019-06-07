package com.github.saphyra.randwo.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handleEmptyRequestBody() {
        return ResponseEntity.badRequest().body(errorResponse(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_BODY_MISSING));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ErrorResponse> handleMethodNotSupported() {
        return new ResponseEntity(errorResponse(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.GENERAL_ERROR), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ErrorResponse errorResponse(HttpStatus httpStatus, ErrorCode errorCode) {
        return ErrorResponse.builder()
            .httpStatus(httpStatus.value())
            .errorCode(errorCode.getErrorCode())
            .build();
    }
}
