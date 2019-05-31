package com.github.saphyra.randwo.item.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.Item;

@RunWith(MockitoJUnitRunner.class)
public class ItemConverterTest {
    private static final UUID ITEM_ID = UUID.randomUUID();

    @InjectMocks
    private ItemConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        ItemEntity entity = ItemEntity.builder()
            .itemId(ITEM_ID)
            .build();
        //WHEN
        Item result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        Item item = Item.builder()
            .itemId(ITEM_ID)
            .build();
        //WHEN
        ItemEntity result = underTest.processDomainConversion(item);
        //THEN
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
    }
}