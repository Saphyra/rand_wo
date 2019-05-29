package com.github.saphyra.randwo.item;

import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.service.create.CreateItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    public static final String SAVE_ITEM_MAPPING = "/item";

    private final CreateItemService createItemService;

    @PutMapping(SAVE_ITEM_MAPPING)
    void createItem(@RequestBody @Valid ItemRequest itemRequest) {
        log.info("Creating item {}", itemRequest);
        createItemService.createItem(itemRequest);
    }
}
