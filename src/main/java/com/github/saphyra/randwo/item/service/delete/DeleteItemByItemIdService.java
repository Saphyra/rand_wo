package com.github.saphyra.randwo.item.service.delete;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteItemByItemIdService {
    private final CollectionValidator collectionValidator;
    private final DeleteItemService deleteItemService;

    @Transactional
    public void deleteItems(List<UUID> itemIds) {
        if (itemIds.isEmpty()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.EMPTY_ITEM_IDS.getErrorCode()), "itemIds is empty.");
        }
        collectionValidator.validateDoesNotContainNull(itemIds, ErrorCode.NULL_ITEM_ID);
        itemIds.forEach(deleteItemService::delete);
    }
}
