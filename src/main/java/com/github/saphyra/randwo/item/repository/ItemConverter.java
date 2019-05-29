package com.github.saphyra.randwo.item.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemValue;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemConverter extends ConverterBase<ItemEntity, Item> {
    private final ObjectMapperDelegator objectMapperDelegator;

    @Override
    protected Item processEntityConversion(ItemEntity itemEntity) {
        return Item.builder()
            .itemId(itemEntity.getItemId())
            .values(objectMapperDelegator.readMapValue(itemEntity.getValues(), ItemValue.class))
            .build();
    }

    @Override
    protected ItemEntity processDomainConversion(Item item) {
        return ItemEntity.builder()
            .itemId(item.getItemId())
            .values(objectMapperDelegator.writeValueAsString(item.getValues()))
            .build();
    }
}
