package com.github.saphyra.randwo.item.service.delete;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class DeleteItemServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private DeleteItemService underTest;

    @Test
    public void delete() {
        //WHEN
        underTest.delete(ITEM_ID);
        //THEN
        verify(itemDao).deleteById(ITEM_ID);
        verify(itemLabelMappingDao).deleteByItemId(ITEM_ID);
        verify(itemValueMappingDao).deleteByItemId(ITEM_ID);
    }
}