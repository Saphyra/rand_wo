package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
///todo unit test
public class ContainsAllItemDeletionProcessor implements ItemDeletionProcessor {
    private final DeleteItemService deleteItemService;
    private final ItemLabelMappingDao itemLabelMappingDao;

    @Override
    public boolean canProcess(ItemDeleteMethod itemDeleteMethod) {
        return itemDeleteMethod.equals(ItemDeleteMethod.CONTAINS_ALL);
    }

    @Override
    public void process(DeleteItemRequest request) {
        collectItemIds(request.getLabelIds()).stream()
            .filter(itemId -> shouldDelete(itemId, request.getLabelIds()))
            .forEach(deleteItemService::delete);
    }

    private List<UUID> collectItemIds(List<UUID> labelIds) {
        return labelIds.stream()
            .flatMap(labelId -> itemLabelMappingDao.getByLabelId(labelId).stream())
            .map(ItemLabelMapping::getItemId)
            .distinct()
            .collect(Collectors.toList());
    }

    private boolean shouldDelete(UUID itemId, List<UUID> labelIds) {
        return labelIds.stream()
            .allMatch(labelId -> itemLabelMappingDao.findByItemIdAndLabelId(itemId, labelId).isPresent());
    }
}
