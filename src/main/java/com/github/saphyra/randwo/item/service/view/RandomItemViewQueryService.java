package com.github.saphyra.randwo.item.service.view;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.domain.RandomItemRequest;
import com.github.saphyra.randwo.item.service.ItemQueryService;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomItemViewQueryService {
    private final CollectionValidator collectionValidator;
    private final ItemQueryService itemQueryService;
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;
    private final ItemViewConverter itemViewConverter;
    private final Random random;

    public ItemView getRandomItem(RandomItemRequest request) {
        collectionValidator.validateDoesNotContainNull(request.getLabelIds(), ErrorCode.NULL_IN_LABEL_IDS);
        collectionValidator.validateDoesNotContainNull(request.getKeyIds(), ErrorCode.NULL_IN_KEY_IDS);

        List<UUID> itemIds = request.getLabelIds().stream()
            //Get itemIds with the given labels
            .flatMap(labelId -> itemLabelMappingDao.getByLabelId(labelId).stream())
            .map(ItemLabelMapping::getItemId)
            .distinct()
            //Filtering itemIds what contains any of the given columns
            .filter(itemId -> request.getKeyIds().stream()
                .anyMatch(keyId -> itemValueMappingDao.findByItemIdAndKeyId(itemId, keyId).isPresent()))
            .collect(Collectors.toList());

        if (itemIds.isEmpty()) {
            throw new NotFoundException(new ErrorMessage(ErrorCode.RANDOM_ITEM_CANNOT_BE_SELECTED.getErrorCode()), "Random item cannot be selected due to too strict filtering.");
        }

        int randomIndex = random.randInt(0, itemIds.size() - 1);
        log.info("Selecting item with index {}. Number of items: {}", randomIndex, itemIds.size());
        UUID selectedItemId = itemIds.get(randomIndex);
        return itemViewConverter.convert(itemQueryService.findByItemIdValidated(selectedItemId));
    }
}
