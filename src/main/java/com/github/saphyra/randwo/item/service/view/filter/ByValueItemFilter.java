package com.github.saphyra.randwo.item.service.view.filter;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ByValueItemFilter implements ItemFilter {
    private final ItemValueMappingDao itemValueMappingDao;

    @Override
    public boolean test(Item item, GetItemsRequest request) {
        String searchByValue = request.getSearchByValue();
        if (isEmpty(searchByValue)) {
            return true;
        }

        return itemValueMappingDao.getByItemId(item.getItemId()).stream()
            .anyMatch(itemValueMapping -> itemValueMapping.getValue().toLowerCase().contains(searchByValue.toLowerCase()));
    }
}
