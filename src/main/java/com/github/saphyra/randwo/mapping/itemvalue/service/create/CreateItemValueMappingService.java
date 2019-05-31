package com.github.saphyra.randwo.mapping.itemvalue.service.create;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateItemValueMappingService {
    private final ItemValueMappingDao itemValueMappingDao;
    private final ItemValueMappingFactory itemValueMappingFactory;

    public void create(UUID itemId, Map<UUID, String> keyValues) {
        keyValues.forEach((keyId, value) -> create(itemId, keyId, value));
    }

    private void create(UUID itemId, UUID keyId, String value) {
        if(itemValueMappingDao.findByItemIdAndKeyId(itemId, keyId).isPresent()){
            throw new ConflictException(
                new ErrorMessage(ErrorCode.ITEM_VALUE_MAPPING_ALREADY_EXISTS.getErrorCode()),
                String.format("Mapping already exists between item %s and key %s", itemId, keyId)
            );
        }

        ItemValueMapping mapping = itemValueMappingFactory.create(itemId, keyId, value);
        itemValueMappingDao.save(mapping);
    }
}
