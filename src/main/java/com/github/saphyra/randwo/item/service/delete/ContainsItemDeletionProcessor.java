package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor

public class ContainsItemDeletionProcessor implements ItemDeletionProcessor {
    private final ItemDao itemDao;
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;

    @Override
    public boolean canProcess(ItemDeleteMethod itemDeleteMethod) {
        return itemDeleteMethod.equals(ItemDeleteMethod.CONTAINS);
    }

    @Override
    public void process(DeleteItemRequest request) {
        request.getLabelIds().forEach(this::deleteByLabelId);
    }

    private void deleteByLabelId(UUID labelId) {
        itemLabelMappingDao.getByLabelId(labelId).forEach(this::deleteByMapping);
    }

    private void deleteByMapping(ItemLabelMapping itemLabelMapping) {
        itemDao.deleteById(itemLabelMapping.getItemId());
        itemValueMappingDao.deleteByItemId(itemLabelMapping.getItemId());
        itemLabelMappingDao.delete(itemLabelMapping);
    }
}
