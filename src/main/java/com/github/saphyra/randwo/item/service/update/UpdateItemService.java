package com.github.saphyra.randwo.item.service.update;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.common.CollectionAggregator;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.ItemQueryService;
import com.github.saphyra.randwo.item.service.NewKeySaverService;
import com.github.saphyra.randwo.item.service.NewLabelSaverService;
import com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.service.UpdateMappingService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateItemService {
    private final ItemDao itemDao;
    private final ItemRequestValidator itemRequestValidator;
    private final ItemQueryService itemQueryService;
    private final CollectionAggregator collectionAggregator;
    private final NewKeySaverService newKeySaverService;
    private final NewLabelSaverService newLabelSaverService;
    private final UpdateMappingService updateMappingService;

    @Transactional
    public void updateItem(UUID itemId, ItemRequest itemRequest) {
        itemRequestValidator.validate(itemRequest);
        Item item = itemQueryService.findByItemIdValidated(itemId);

        List<UUID> newLabelIds = newLabelSaverService.saveLabels(itemRequest.getNewLabels());
        Map<UUID, String> newKeys = newKeySaverService.saveKeys(itemRequest.getNewKeyValues());

        item.setValues(collectionAggregator.aggregate(itemRequest.getExistingKeyValueIds(), newKeys));

        updateMappingService.updateMappings(
            item.getItemId(),
            collectionAggregator.aggregate(itemRequest.getExistingLabelIds(), newLabelIds)
        );

        itemDao.save(item);
    }
}
