package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteItemService {
    private final CollectionValidator collectionValidator;
    private final ItemDao itemDao;
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;

    @Transactional
    public void deleteItems(List<UUID> itemIds) {
        if (itemIds.isEmpty()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.EMPTY_ITEM_IDS.getErrorCode()), "itemIds is empty.");
        }
        collectionValidator.validateDoesNotContainNull(itemIds, ErrorCode.NULL_ITEM_ID.getErrorCode());
        itemIds.forEach(this::delete);
    }

    private void delete(UUID itemId) {
        itemLabelMappingDao.deleteByItemId(itemId);
        itemValueMappingDao.deleteByItemId(itemId);
        itemDao.deleteById(itemId);
    }
}
