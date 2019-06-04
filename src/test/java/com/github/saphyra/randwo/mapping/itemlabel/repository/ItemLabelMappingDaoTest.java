package com.github.saphyra.randwo.mapping.itemlabel.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;

@RunWith(MockitoJUnitRunner.class)
public class ItemLabelMappingDaoTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private ItemLabelMappingRepository itemLabelMappingRepository;

    @Mock
    private ItemLabelMappingConverter itemLabelMappingConverter;

    @InjectMocks
    private ItemLabelMappingDao underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Mock
    private ItemLabelMappingEntity itemLabelMappingEntity;

    @Test
    public void deleteByItemId() {
        //WHEN
        underTest.deleteByItemId(ITEM_ID);
        //THEN
        verify(itemLabelMappingRepository).deleteByItemId(ITEM_ID);
    }

    @Test
    public void deleteByLabelId() {
        //WHEN
        underTest.deleteByLabelId(LABEL_ID);
        //THEN
        verify(itemLabelMappingRepository).deleteByLabelId(LABEL_ID);
    }

    @Test
    public void findByItemIdAndLabelId() {
        //GIVEN
        Optional<ItemLabelMappingEntity> entityOptional = Optional.of(this.itemLabelMappingEntity);
        given(itemLabelMappingRepository.findByItemIdAndLabelId(ITEM_ID, LABEL_ID)).willReturn(entityOptional);
        Optional<ItemLabelMapping> domainOptional = Optional.of(this.itemLabelMapping);
        given(itemLabelMappingConverter.convertEntity(entityOptional)).willReturn(domainOptional);
        //WHEN
        Optional<ItemLabelMapping> result = underTest.findByItemIdAndLabelId(ITEM_ID, LABEL_ID);
        //THEN
        assertThat(result).contains(this.itemLabelMapping);
    }

    @Test
    public void getByItemId() {
        //GIVEN
        List<ItemLabelMappingEntity> mappingEntities = Arrays.asList(itemLabelMappingEntity);
        given(itemLabelMappingRepository.getByItemId(ITEM_ID)).willReturn(mappingEntities);

        given(itemLabelMappingConverter.convertEntity(mappingEntities)).willReturn(Arrays.asList(itemLabelMapping));
        //WHEN
        List<ItemLabelMapping> result = underTest.getByItemId(ITEM_ID);
        //THEN
        assertThat(result).contains(itemLabelMapping);
    }

    @Test
    public void getByLabelId() {
        //GIVEN
        List<ItemLabelMappingEntity> entities = Arrays.asList(itemLabelMappingEntity);
        given(itemLabelMappingRepository.getByLabelId(LABEL_ID)).willReturn(entities);

        List<ItemLabelMapping> mappings = Arrays.asList(itemLabelMapping);
        given(itemLabelMappingConverter.convertEntity(entities)).willReturn(mappings);
        //WHEN
        List<ItemLabelMapping> result = underTest.getByLabelId(LABEL_ID);
        //THEN
        assertThat(result).isEqualTo(mappings);
    }
}