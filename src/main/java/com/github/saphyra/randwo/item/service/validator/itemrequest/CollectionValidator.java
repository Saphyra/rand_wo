package com.github.saphyra.randwo.item.service.validator.itemrequest;

import static com.github.saphyra.randwo.common.ExceptionFactory.createNullException;
import static java.util.Objects.isNull;

import java.util.Collection;

import org.springframework.stereotype.Component;

@Component
class CollectionValidator {
    <T> void validateDoesNotContainNull(Collection<T> collection, String nullValue) {
        collection.forEach(item -> validateItem(item, nullValue));
    }

    private <T> void validateItem(T item, String nullValue) {
        if (isNull(item)) {
            throw createNullException(nullValue);
        }
    }
}
