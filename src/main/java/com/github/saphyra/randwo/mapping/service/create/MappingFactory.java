package com.github.saphyra.randwo.mapping.service.create;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.mapping.domain.ItemLabelMapping;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MappingFactory {
    private final IdGenerator idGenerator;

    public ItemLabelMapping create(UUID itemId, UUID labelId) {
        return ItemLabelMapping.builder()
            .mappingId(idGenerator.randomUUID())
            .itemId(itemId)
            .labelId(labelId)
            .build();
    }
}
