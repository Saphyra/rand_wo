package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;

interface ItemDeletionProcessor {
    boolean canProcess(ItemDeleteMethod itemDeleteMethod);

    void process(DeleteItemRequest request);
}
