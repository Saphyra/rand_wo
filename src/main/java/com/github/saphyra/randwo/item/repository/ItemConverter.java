package com.github.saphyra.randwo.item.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.item.domain.Item;

@Component
public class ItemConverter extends ConverterBase<ItemEntity, Item> {
    @Override
    protected Item processEntityConversion(ItemEntity itemEntity) {
        return Item.builder()
            .itemId(itemEntity.getItemId())
            .build();
    }

    @Override
    protected ItemEntity processDomainConversion(Item item) {
        return ItemEntity.builder()
            .itemId(item.getItemId())
            .build();
    }
}
