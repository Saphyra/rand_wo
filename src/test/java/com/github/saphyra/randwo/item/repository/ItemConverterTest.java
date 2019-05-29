package com.github.saphyra.randwo.item.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemValue;

@RunWith(MockitoJUnitRunner.class)
public class ItemConverterTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String VALUES_STRING = "values_string";

    @Mock
    private ObjectMapperDelegator objectMapperDelegator;

    @InjectMocks
    private ItemConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        ItemValue values = new ItemValue();
        values.put(KEY, VALUE);
        ItemEntity entity = ItemEntity.builder()
            .itemId(ITEM_ID)
            .values(VALUES_STRING)
            .build();
        given(objectMapperDelegator.readMapValue(VALUES_STRING, ItemValue.class)).willReturn(values);
        //WHEN
        Item result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getValues()).isEqualTo(values);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        ItemValue values = new ItemValue();
        values.put(KEY, VALUE);
        Item item = Item.builder()
            .itemId(ITEM_ID)
            .values(values)
            .build();
        given(objectMapperDelegator.writeValueAsString(values)).willReturn(VALUES_STRING);
        //WHEN
        ItemEntity result = underTest.processDomainConversion(item);
        //THEN
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getValues()).isEqualTo(VALUES_STRING);
    }
}