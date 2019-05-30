package com.github.saphyra.randwo.item.service.validator.itemrequest;

import static java.util.Objects.isNull;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.common.ExceptionFactory;
import com.github.saphyra.randwo.key.repository.KeyDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ValueValidator {
    public static final String NULL_EXISTING_KEY_VALUES = "existing-key-values";
    public static final String NULL_IN_EXISTING_KEY_VALUES = "null-in-existing-key-values";
    public static final String NULL_NEW_KEY_VALUES = "new-key-values";
    public static final String NULL_IN_NEW_KEY_VALUES = "null-in-new-key-values";

    private final CollectionValidator collectionValidator;
    private final KeyDao keyDao;

    void validate(Map<UUID, String> existingKeyValues, Map<String, String> newKeyValues) {
        if (isNull(existingKeyValues)) {
            throw ExceptionFactory.createNullException(NULL_EXISTING_KEY_VALUES);
        }

        if (isNull(newKeyValues)) {
            throw ExceptionFactory.createNullException(NULL_NEW_KEY_VALUES);
        }

        if (existingKeyValues.isEmpty() && newKeyValues.isEmpty()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.NO_ITEM_VALUES.getErrorCode()), "Item must contain at least one value.");
        }

        collectionValidator.validateDoesNotContainNull(existingKeyValues.values(), NULL_IN_EXISTING_KEY_VALUES);
        collectionValidator.validateDoesNotContainNull(newKeyValues.values(), NULL_IN_NEW_KEY_VALUES);

        existingKeyValues.keySet().forEach(this::validateKeyExists);
    }

    private void validateKeyExists(UUID keyId) {
        if (!keyDao.findById(keyId).isPresent()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.KEY_NOT_FOUND.getErrorCode()), "Key not found with keyId " + keyId);
        }
    }
}
