package com.github.saphyra.randwo.storedobject.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;

@Component
public class StoredObjectConverter extends ConverterBase<StoredObjectEntity, StoredObject> {
    @Override
    protected StoredObject processEntityConversion(StoredObjectEntity entity) {
        return StoredObject.builder()
            .key(entity.getKey())
            .value(entity.getValue())
            .build();
    }

    @Override
    protected StoredObjectEntity processDomainConversion(StoredObject domain) {
        return StoredObjectEntity.builder()
            .key(domain.getKey())
            .value(domain.getValue())
            .build();
    }
}
