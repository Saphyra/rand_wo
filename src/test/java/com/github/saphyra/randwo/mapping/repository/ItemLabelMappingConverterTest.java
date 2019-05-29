package com.github.saphyra.randwo.mapping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.domain.ItemLabelMapping;

@RunWith(MockitoJUnitRunner.class)
public class ItemLabelMappingConverterTest {
    private static final UUID MAPPING_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();

    @InjectMocks
    private ItemLabelMappingConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        ItemLabelMappingEntity entity = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID)
            .itemId(ITEM_ID)
            .labelId(LABEL_ID)
            .build();
        //WHEN
        ItemLabelMapping result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getMappingId()).isEqualTo(MAPPING_ID);
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        ItemLabelMapping domain = ItemLabelMapping.builder()
            .mappingId(MAPPING_ID)
            .itemId(ITEM_ID)
            .labelId(LABEL_ID)
            .build();
        //WHEN
        ItemLabelMappingEntity result = underTest.processDomainConversion(domain);
        //THEN
        assertThat(result.getMappingId()).isEqualTo(MAPPING_ID);
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
    }
}