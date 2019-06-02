package com.github.saphyra.randwo.item;

import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.service.create.CreateItemService;
import com.github.saphyra.randwo.item.service.delete.DeleteItemService;
import com.github.saphyra.randwo.item.service.update.UpdateItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private CreateItemService createItemService;

    @Mock
    private DeleteItemService deleteItemService;

    @Mock
    private UpdateItemService updateItemService;

    @InjectMocks
    private ItemController underTest;

    @Mock
    private ItemRequest itemRequest;

    @Test
    public void createItem() {
        //WHEN
        underTest.createItem(itemRequest);
        //THEN
        verify(createItemService).createItem(itemRequest);
    }

    @Test
    public void deleteByIds() {
        //GIVEN
        List<UUID> itemIds = Arrays.asList(ITEM_ID);
        //WHEN
        underTest.deleteByIds(itemIds);
        //THEN
        verify(deleteItemService).deleteItems(itemIds);
    }

    @Test
    public void updateItem() {
        //WHEN
        underTest.updateItem(ITEM_ID, itemRequest);
        //THEN
        verify(updateItemService).updateItem(ITEM_ID, itemRequest);
    }
}