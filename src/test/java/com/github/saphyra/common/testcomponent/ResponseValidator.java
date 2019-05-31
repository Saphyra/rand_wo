package com.github.saphyra.common.testcomponent;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import lombok.RequiredArgsConstructor;

@TestComponent
@RequiredArgsConstructor
public class ResponseValidator {
    private final ObjectMapperDelegator objectMapperDelegator;

    public ErrorResponse verifyBadRequest(MockHttpServletResponse result, ErrorCode errorCode) throws UnsupportedEncodingException {
        return verifyErrorResponse(result, HttpStatus.BAD_REQUEST, errorCode);
    }

    public ErrorResponse verifyNotFoundRequest(MockHttpServletResponse result, ErrorCode errorCode) throws UnsupportedEncodingException {
        return verifyErrorResponse(result, HttpStatus.NOT_FOUND, errorCode);
    }

    public ErrorResponse verifyErrorResponse(MockHttpServletResponse result, HttpStatus status, ErrorCode errorCode) throws UnsupportedEncodingException {
        assertThat(result.getStatus()).isEqualTo(status.value());
        ErrorResponse response = objectMapperDelegator.readValue(result.getContentAsString(), ErrorResponse.class);
        assertThat(response.getErrorCode()).isEqualTo(errorCode.getErrorCode());
        return response;
    }
}
