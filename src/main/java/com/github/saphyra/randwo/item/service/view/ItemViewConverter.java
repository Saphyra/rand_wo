package com.github.saphyra.randwo.item.service.view;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ItemViewConverter {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;

    ItemView convert(Item item) {
        return ItemView.builder()
            .itemId(item.getItemId())
            .labelIds(fetchLabelIds(item))
            .columns(fetchColumns(item))
            .build();
    }

    private List<UUID> fetchLabelIds(Item item) {
        return itemLabelMappingDao.getByItemId(item.getItemId()).stream()
            .map(ItemLabelMapping::getLabelId)
            .collect(Collectors.toList());
    }

    private Map<UUID, String> fetchColumns(Item item) {
        return itemValueMappingDao.getByItemId(item.getItemId()).stream()
            .collect(Collectors.toMap(ItemValueMapping::getKeyId, ItemValueMapping::getValue));
    }
}
