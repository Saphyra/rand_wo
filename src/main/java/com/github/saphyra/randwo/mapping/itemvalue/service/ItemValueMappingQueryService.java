package com.github.saphyra.randwo.mapping.itemvalue.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemValueMappingQueryService {
    private final ItemValueMappingDao itemValueMappingDao;

    public ItemValueMapping findByItemIdAndKeyIdValidated(UUID itemId, UUID keyId) {
        return itemValueMappingDao.findByItemIdAndKeyId(itemId, keyId)
            .orElseThrow(() -> new NotFoundException(
                new ErrorMessage(ErrorCode.ITEM_VALUE_MAPPING_NOT_FOUND.getErrorCode()),
                "ItemValueMapping not found with itemId " + itemId + " and keyId " + keyId
            ));
    }
}
