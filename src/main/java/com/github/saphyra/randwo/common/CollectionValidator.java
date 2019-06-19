package com.github.saphyra.randwo.common;

import static java.util.Objects.isNull;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;

@Component
public class CollectionValidator {
    public <T> void validateDoesNotContainNull(Collection<T> collection, ErrorCode errorCode) {
        collection.forEach(item -> validateItem(item, errorCode));
    }

    private <T> void validateItem(T item, ErrorCode errorCode) {
        if (isNull(item)) {
            throw new BadRequestException(new ErrorMessage(errorCode.getErrorCode()), "Collection contains null: " + errorCode);
        }
    }
}
