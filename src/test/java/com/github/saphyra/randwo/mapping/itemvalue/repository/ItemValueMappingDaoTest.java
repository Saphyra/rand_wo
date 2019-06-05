package com.github.saphyra.randwo.mapping.itemvalue.repository;

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

import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;

@RunWith(MockitoJUnitRunner.class)
public class ItemValueMappingDaoTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();

    @Mock
    private ItemValueMappingRepository repository;

    @Mock
    private ItemValueMappingConverter converter;

    @InjectMocks
    private ItemValueMappingDao underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Mock
    private ItemValueMappingEntity itemValueMappingEntity;

    @Test
    public void deleteByItemId() {
        //WHEN
        underTest.deleteByItemId(ITEM_ID);
        //THEN
        verify(repository).deleteByItemId(ITEM_ID);
    }

    @Test
    public void deleteByKeyId() {
        //WHEN
        underTest.deleteByKeyId(KEY_ID);
        //THEN
        verify(repository).deleteByKeyId(KEY_ID);
    }

    @Test
    public void findByItemIdAndKeyId() {
        //GIVEN
        Optional<ItemValueMappingEntity> entityOptional = Optional.of(this.itemValueMappingEntity);
        given(repository.findByItemIdAndKeyId(ITEM_ID, KEY_ID)).willReturn(entityOptional);

        given(converter.convertEntity(entityOptional)).willReturn(Optional.of(itemValueMapping));
        //WHEN
        Optional<ItemValueMapping> result = underTest.findByItemIdAndKeyId(ITEM_ID, KEY_ID);
        //THEN
        assertThat(result).contains(itemValueMapping);
    }

    @Test
    public void getByItemId() {
        //GIVEN
        List<ItemValueMappingEntity> entities = Arrays.asList(itemValueMappingEntity);
        given(repository.getByItemId(ITEM_ID)).willReturn(entities);

        List<ItemValueMapping> domains = Arrays.asList(itemValueMapping);
        given(converter.convertEntity(entities)).willReturn(domains);
        //WHEN
        List<ItemValueMapping> result = underTest.getByItemId(ITEM_ID);
        //THEN
        assertThat(result).isEqualTo(domains);
    }

    @Test
    public void getByKeyId() {
        //GIVEN
        List<ItemValueMappingEntity> entities = Arrays.asList(itemValueMappingEntity);
        given(repository.getByKeyId(KEY_ID)).willReturn(entities);

        List<ItemValueMapping> domains = Arrays.asList(itemValueMapping);
        given(converter.convertEntity(entities)).willReturn(domains);
        //WHEN
        List<ItemValueMapping> result = underTest.getByKeyId(KEY_ID);
        //THEN
        assertThat(result).isEqualTo(domains);
    }
}