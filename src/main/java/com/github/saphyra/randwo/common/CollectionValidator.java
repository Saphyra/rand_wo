package com.github.saphyra.randwo.common;

import static com.github.saphyra.randwo.common.ExceptionFactory.createNullException;
import static java.util.Objects.isNull;

import java.util.Collection;

import org.springframework.stereotype.Component;

@Component
public class CollectionValidator {
    public <T> void validateDoesNotContainNull(Collection<T> collection, ErrorCode nullValue) {
        collection.forEach(item -> validateItem(item, nullValue));
    }

    private <T> void validateItem(T item, ErrorCode nullValue) {
        if (isNull(item)) {
            throw createNullException(nullValue);
        }
    }
}
