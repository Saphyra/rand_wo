package com.github.saphyra.randwo.common;

import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.github.saphyra.randwo.common.ExceptionFactory.createNullException;
import static java.util.Objects.isNull;

@Component
public class CollectionValidator {
    public <T> void validateDoesNotContainNull(Collection<T> collection, String nullValue) {
        collection.forEach(item -> validateItem(item, nullValue));
    }

    private <T> void validateItem(T item, String nullValue) {
        if (isNull(item)) {
            throw createNullException(nullValue);
        }
    }
}