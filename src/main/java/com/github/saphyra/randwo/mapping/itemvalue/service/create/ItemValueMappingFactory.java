package com.github.saphyra.randwo.mapping.itemvalue.service.create;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemValueMappingFactory {
    private final IdGenerator idGenerator;

    public ItemValueMapping create(UUID itemId, UUID keyId, String value) {
        return ItemValueMapping.builder()
            .mappingId(idGenerator.randomUUID())
            .itemId(itemId)
            .keyId(keyId)
            .value(value)
            .build();
    }
}
