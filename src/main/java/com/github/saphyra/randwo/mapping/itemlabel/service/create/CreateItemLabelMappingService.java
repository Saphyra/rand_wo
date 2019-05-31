package com.github.saphyra.randwo.mapping.itemlabel.service.create;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateItemLabelMappingService {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemLabelMappingFactory itemLabelMappingFactory;

    public void create(UUID itemId, List<UUID> labelIds) {
        labelIds.forEach(labelId -> create(itemId, labelId));
    }

    private void create(UUID itemId, UUID labelId) {
        if (itemLabelMappingDao.findByItemIdAndLabelId(itemId, labelId).isPresent()) {
            throw new ConflictException(
                new ErrorMessage(ErrorCode.ITEM_LABEL_MAPPING_ALREADY_EXISTS.getErrorCode()),
                String.format("Mapping already exists between item %s and label %s", itemId, labelId)
            );
        }

        ItemLabelMapping mapping = itemLabelMappingFactory.create(itemId, labelId);
        itemLabelMappingDao.save(mapping);
    }
}
