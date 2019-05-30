package com.github.saphyra.randwo.item.service.create;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.service.create.MappingCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateItemService {
    private final ItemRequestValidator itemRequestValidator;
    private final ItemDao itemDao;
    private final ItemFactory itemFactory;
    private final MappingCreationService mappingCreationService;
    private final NewKeySaverService newKeySaverService;
    private final NewLabelSaverService newLabelSaverService;

    @Transactional
    public void createItem(ItemRequest itemRequest) {
        itemRequestValidator.validate(itemRequest);

        List<UUID> newLabelIds = newLabelSaverService.saveLabels(itemRequest.getNewLabels());
        Map<UUID, String> newKeys = newKeySaverService.saveKeys(itemRequest.getNewKeyValues());

        Item item = itemFactory.create(map(itemRequest.getExistingKeyValueIds(), newKeys));

        mappingCreationService.createMapping(
            item.getItemId(),
            Stream.concat(itemRequest.getExistingLabelIds().stream(), newLabelIds.stream())
                .collect(Collectors.toList())
        );
        itemDao.save(item);
    }

    private Map<UUID, String> map(Map<UUID, String> existingKeyValueIds, Map<UUID, String> newKeyIds) {
        Map<UUID, String> result = new HashMap<>(existingKeyValueIds);
        result.putAll(newKeyIds);
        return result;
    }
}
