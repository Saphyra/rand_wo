package com.github.saphyra.randwo.item.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.Item;

@RunWith(MockitoJUnitRunner.class)
public class ItemDaoTest {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemConverter itemConverter;

    @InjectMocks
    private ItemDao underTest;

    @Mock
    private ItemEntity itemEntity;

    @Mock
    private Item item;

    @Test
    public void getAll() {
        //GIVEN
        List<ItemEntity> entities = Arrays.asList(itemEntity);
        given(itemRepository.findAll()).willReturn(entities);

        given(itemConverter.convertEntity(entities)).willReturn(Arrays.asList(item));
        //WHEN
        List<Item> result = underTest.getAll();
        //THEN
        assertThat(result).containsOnly(item);
    }
}