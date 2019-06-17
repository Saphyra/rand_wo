package com.github.saphyra.randwo.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.domain.RandomItemRequest;
import com.github.saphyra.randwo.item.service.create.CreateItemService;
import com.github.saphyra.randwo.item.service.delete.DeleteItemByItemIdService;
import com.github.saphyra.randwo.item.service.delete.DeleteItemByLabelService;
import com.github.saphyra.randwo.item.service.update.UpdateItemService;
import com.github.saphyra.randwo.item.service.view.ItemViewQueryService;
import com.github.saphyra.randwo.item.service.view.RandomItemViewQueryService;

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
    private ItemViewQueryService itemViewQueryService;

    @Mock
    private RandomItemViewQueryService randomItemViewQueryService;

    @Mock
    private UpdateItemService updateItemService;

    @InjectMocks
    private ItemController underTest;

    @Mock
    private ItemRequest itemRequest;

    @Mock
    private DeleteItemRequest deleteItemRequest;

    @Mock
    private GetItemsRequest getItemsRequest;

    @Mock
    private ItemView itemView;

    @Mock
    private RandomItemRequest randomItemRequest;

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
    public void getItems() {
        //GIVEN
        given(itemViewQueryService.getItems(getItemsRequest)).willReturn(Arrays.asList(itemView));
        //WHEN
        List<ItemView> result = underTest.getItems(getItemsRequest);
        //THEN
        assertThat(result).containsOnly(itemView);
    }

    @Test
    public void getRandomItem() {
        //GIVEN
        given(randomItemViewQueryService.getRandomItem(randomItemRequest)).willReturn(itemView);
        //WHEN
        ItemView result = underTest.getRandomItem(randomItemRequest);
        //THEN
        assertThat(result).isEqualTo(itemView);
    }

    @Test
    public void updateItem() {
        //WHEN
        underTest.updateItem(ITEM_ID, itemRequest);
        //THEN
        verify(updateItemService).updateItem(ITEM_ID, itemRequest);
    }
}