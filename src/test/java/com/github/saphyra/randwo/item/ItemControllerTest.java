package com.github.saphyra.randwo.item;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.service.create.CreateItemService;
import com.github.saphyra.randwo.item.service.delete.DeleteItemByItemIdService;
import com.github.saphyra.randwo.item.service.delete.DeleteItemByLabelService;
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
    private DeleteItemByItemIdService deleteItemByItemIdService;

    @Mock
    private DeleteItemByLabelService deleteItemByLabelService;

    @Mock
    private UpdateItemService updateItemService;

    @InjectMocks
    private ItemController underTest;

    @Mock
    private ItemRequest itemRequest;

    @Mock
    private DeleteItemRequest deleteItemRequest;

    @Test
    public void createItem() {
        //WHEN
        underTest.createItem(itemRequest);
        //THEN
        verify(createItemService).createItem(itemRequest);
    }

    @Test
    public void deleteByItemIds() {
        //GIVEN
        List<UUID> itemIds = Arrays.asList(ITEM_ID);
        //WHEN
        underTest.deleteByItemIds(itemIds);
        //THEN
        verify(deleteItemByItemIdService).deleteItems(itemIds);
    }

    @Test
    public void deleteByLabelIds() {
        //WHEN
        underTest.deleteByLabelIds(deleteItemRequest);
        //THEN
        verify(deleteItemByLabelService).deleteItems(deleteItemRequest);
    }

    @Test
    public void updateItem() {
        //WHEN
        underTest.updateItem(ITEM_ID, itemRequest);
        //THEN
        verify(updateItemService).updateItem(ITEM_ID, itemRequest);
    }
}