package com.github.saphyra.randwo.key.service;

import static org.apache.logging.log4j.util.Strings.isBlank;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.repository.KeyDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeyValueValidator {
    private final KeyDao keyDao;

    public void validate(String keyValue) {
        if (isBlank(keyValue)) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.EMPTY_KEY_VALUE.getErrorCode()), "Key value must not be empty.");
        }

        if (keyDao.findByKeyValue(keyValue).isPresent()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.KEY_VALUE_ALREADY_EXISTS.getErrorCode()), "Key already exists with value " + keyValue);
        }
    }
}
