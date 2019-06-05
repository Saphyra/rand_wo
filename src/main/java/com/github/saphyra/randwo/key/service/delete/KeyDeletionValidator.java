package com.github.saphyra.randwo.key.service.delete;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.UnprocessableEntityException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class KeyDeletionValidator {
    private final ItemValueMappingDao itemValueMappingDao;

    void validateKeyCanBeDeleted(UUID keyId) {
        itemValueMappingDao.getByKeyId(keyId).forEach(this::validate);
    }

    private void validate(ItemValueMapping itemValueMapping) {
        if(itemValueMappingDao.getByItemId(itemValueMapping.getItemId()).size() == 1){
            throw new UnprocessableEntityException(
                new ErrorMessage(ErrorCode.ITEM_HAS_ONLY_ONE_KEY.getErrorCode()),
                "Key with id " + itemValueMapping.getKeyId() + " cannot be deleted because there are items with only this key."
            );
        }
    }
}
