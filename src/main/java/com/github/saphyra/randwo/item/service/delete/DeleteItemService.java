package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
///todo unit test
class DeleteItemService {
    private final ItemDao itemDao;
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;

    void delete(UUID itemId) {
        itemLabelMappingDao.deleteByItemId(itemId);
        itemValueMappingDao.deleteByItemId(itemId);
        itemDao.deleteById(itemId);
    }
}
