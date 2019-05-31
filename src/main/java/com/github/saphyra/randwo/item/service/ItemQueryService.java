package com.github.saphyra.randwo.item.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.repository.ItemDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemQueryService {
    private final ItemDao itemDao;

    public Item findByItemIdValidated(UUID itemId) {
        return itemDao.findById(itemId)
            .orElseThrow(() -> new NotFoundException(new ErrorMessage(ErrorCode.ITEM_NOT_FOUND.getErrorCode()), "Item not found with itemId " + itemId));
    }
}
