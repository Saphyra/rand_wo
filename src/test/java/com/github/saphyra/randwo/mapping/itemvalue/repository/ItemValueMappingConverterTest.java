package com.github.saphyra.randwo.mapping.itemvalue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;

@RunWith(MockitoJUnitRunner.class)
public class ItemValueMappingConverterTest {
    private static final UUID MAPPING_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String VALUE = "value";

    @InjectMocks
    private ItemValueMappingConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        ItemValueMappingEntity entity = ItemValueMappingEntity.builder()
            .mappingId(MAPPING_ID)
            .itemId(ITEM_ID)
            .keyId(KEY_ID)
            .value(VALUE)
            .build();
        //WHEN
        ItemValueMapping result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getMappingId()).isEqualTo(MAPPING_ID);
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        ItemValueMapping domain = ItemValueMapping.builder()
            .mappingId(MAPPING_ID)
            .itemId(ITEM_ID)
            .keyId(KEY_ID)
            .value(VALUE)
            .build();
        //WHEN
        ItemValueMappingEntity result = underTest.processDomainConversion(domain);
        //THEN
        assertThat(result.getMappingId()).isEqualTo(MAPPING_ID);
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }
}