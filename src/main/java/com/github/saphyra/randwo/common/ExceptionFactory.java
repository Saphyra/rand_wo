package com.github.saphyra.randwo.common;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;

import java.util.HashMap;
import java.util.Map;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionFactory {
    public static BadRequestException createNullException(ErrorCode nullValue) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(PARAMETER_KEY_NULL_VALUE, nullValue.getErrorCode());
        ErrorMessage errorMessage = new ErrorMessage(ErrorCode.VALUE_IS_NULL.getErrorCode(), parameters);
        return new BadRequestException(errorMessage, String.format("%s is null.", nullValue));
    }
}
