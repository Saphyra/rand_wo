package com.github.saphyra.randwo.item;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    public static final String CREATE_ITEM_MAPPING = "/item";
    public static final String DELETE_BY_ITEM_IDS_MAPPING = "/item/id";
    public static final String DELETE_BY_LABEL_IDS_MAPPING = "/item/label";
    public static final String GET_ITEMS_MAPPING = "/item";
    public static final String GET_RANDOM_ITEM_MAPPING = "/item/random";
    public static final String UPDATE_ITEM_MAPPING = "/item/{itemId}";

    private final CreateItemService createItemService;
    private final DeleteItemByItemIdService deleteItemByItemIdService;
    private final DeleteItemByLabelService deleteItemByLabelService;
    private final ItemViewQueryService itemViewQueryService;
    private final RandomItemViewQueryService randomItemViewQueryService;
    private final UpdateItemService updateItemService;

    @PutMapping(CREATE_ITEM_MAPPING)
    void createItem(@RequestBody ItemRequest itemRequest) {
        log.info("Creating item {}", itemRequest);
        createItemService.createItem(itemRequest);
    }

    @DeleteMapping(DELETE_BY_ITEM_IDS_MAPPING)
    public void deleteByItemIds(@RequestBody List<UUID> itemIds) {
        log.info("Deleting items with itemIds {}", itemIds);
        deleteItemByItemIdService.deleteItems(itemIds);
    }

    @DeleteMapping(DELETE_BY_LABEL_IDS_MAPPING)
    public void deleteByLabelIds(@RequestBody DeleteItemRequest request) {
        log.info("Deleting items based on request: {}", request);
        deleteItemByLabelService.deleteItems(request);
    }

    @PostMapping(GET_ITEMS_MAPPING)
    //TODO API test
    public List<ItemView> getItems(@RequestBody GetItemsRequest request) {
        log.info("Querying items: {}", request);
        return itemViewQueryService.getItems(request);
    }

    @PostMapping(GET_RANDOM_ITEM_MAPPING)
    //TODO API test
    public ItemView getRandomItem(@RequestBody RandomItemRequest request){
        log.info("Querying random item for request {}", request);
        return randomItemViewQueryService.getRandomItem(request);
    }

    @PostMapping(UPDATE_ITEM_MAPPING)
    void updateItem(
        @PathVariable("itemId") UUID itemId,
        @RequestBody @Valid ItemRequest itemRequest
    ) {
        log.info("Updating item with id: {}. Request:  {}", itemId, itemRequest);
        updateItemService.updateItem(itemId, itemRequest);
    }
}
