package com.github.saphyra.randwo.item.service.create;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.common.CollectionAggregator;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.NewKeySaverService;
import com.github.saphyra.randwo.item.service.NewLabelSaverService;
import com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.itemlabel.service.create.CreateItemLabelMappingService;
import com.github.saphyra.randwo.mapping.itemvalue.service.create.CreateItemValueMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateItemService {
    private final CollectionAggregator collectionAggregator;
    private final CreateItemLabelMappingService createItemLabelMappingService;
    private final CreateItemValueMappingService createItemValueMappingService;
    private final ItemRequestValidator itemRequestValidator;
    private final ItemDao itemDao;
    private final ItemFactory itemFactory;
    private final NewKeySaverService newKeySaverService;
    private final NewLabelSaverService newLabelSaverService;

    @Transactional
    public void createItem(ItemRequest itemRequest) {
        itemRequestValidator.validate(itemRequest);

        List<UUID> newLabelIds = newLabelSaverService.saveLabels(itemRequest.getNewLabels());
        Map<UUID, String> newKeys = newKeySaverService.saveKeys(itemRequest.getNewKeyValues());

        Item item = itemFactory.create();
        createItemValueMappingService.create(
            item.getItemId(),
            collectionAggregator.aggregate(itemRequest.getExistingKeyValueIds(), newKeys)
        );

        createItemLabelMappingService.create(
            item.getItemId(),
            collectionAggregator.aggregate(itemRequest.getExistingLabelIds(), newLabelIds)
        );
        itemDao.save(item);
    }
}
