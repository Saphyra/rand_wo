package com.github.saphyra.randwo.mapping.itemvalue.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;

@Component
public class ItemValueMappingConverter extends ConverterBase<ItemValueMappingEntity, ItemValueMapping> {
    @Override
    protected ItemValueMapping processEntityConversion(ItemValueMappingEntity entity) {
        return ItemValueMapping.builder()
            .mappingId(entity.getMappingId())
            .itemId(entity.getItemId())
            .keyId(entity.getKeyId())
            .value(entity.getValue())
            .build();
    }

    @Override
    protected ItemValueMappingEntity processDomainConversion(ItemValueMapping domain) {
        return ItemValueMappingEntity.builder()
            .mappingId(domain.getMappingId())
            .itemId(domain.getItemId())
            .keyId(domain.getKeyId())
            .value(domain.getValue())
            .build();
    }
}
