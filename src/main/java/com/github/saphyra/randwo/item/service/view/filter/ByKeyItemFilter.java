package com.github.saphyra.randwo.item.service.view.filter;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ByKeyItemFilter implements ItemFilter {
    private final ItemValueMappingDao itemValueMappingDao;
    private final KeyQueryService keyQueryService;

    @Override
    public boolean test(Item item, GetItemsRequest request) {
        String searchByKey = request.getSearchByKey();
        if (isEmpty(searchByKey)) {
            return true;
        }

        return itemValueMappingDao.getByItemId(item.getItemId()).stream()
            .map(itemValueMapping -> keyQueryService.findByKeyIdValidated(itemValueMapping.getKeyId()))
            .anyMatch(key -> key.getKeyValue().toLowerCase().contains(searchByKey.toLowerCase()));
    }
}
