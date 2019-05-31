package com.github.saphyra.randwo.item;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.service.create.CreateItemService;
import com.github.saphyra.randwo.item.service.update.UpdateItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    public static final String CREATE_ITEM_MAPPING = "/item";
    public static final String UPDATE_ITEM_MAPPING = "/item/{itemId}";

    private final CreateItemService createItemService;
    private final UpdateItemService updateItemService;

    @PutMapping(CREATE_ITEM_MAPPING)
    void createItem(@RequestBody @Valid ItemRequest itemRequest) {
        log.info("Creating item {}", itemRequest);
        createItemService.createItem(itemRequest);
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
