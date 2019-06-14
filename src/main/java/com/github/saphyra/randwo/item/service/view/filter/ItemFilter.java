package com.github.saphyra.randwo.item.service.view.filter;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;

public interface ItemFilter {
    boolean test(Item item, GetItemsRequest request);
}
