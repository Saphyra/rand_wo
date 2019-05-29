package com.github.saphyra.randwo.mapping.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.mapping.domain.ItemLabelMapping;

@Component
public class ItemLabelMappingConverter extends ConverterBase<ItemLabelMappingEntity, ItemLabelMapping> {
    @Override
    protected ItemLabelMapping processEntityConversion(ItemLabelMappingEntity entity) {
        return ItemLabelMapping.builder()
            .mappingId(entity.getMappingId())
            .itemId(entity.getItemId())
            .labelId(entity.getLabelId())
            .build();
    }

    @Override
    protected ItemLabelMappingEntity processDomainConversion(ItemLabelMapping domain) {
        return ItemLabelMappingEntity.builder()
            .mappingId(domain.getMappingId())
            .itemId(domain.getItemId())
            .labelId(domain.getLabelId())
            .build();
    }
}
