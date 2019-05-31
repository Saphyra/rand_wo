package com.github.saphyra.randwo.mapping.itemlabel.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemlabel.service.create.CreateItemLabelMappingService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateItemLabelMappingService {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final CreateItemLabelMappingService createItemLabelMappingService;

    public void update(UUID itemId, List<UUID> labelIds) {
        itemLabelMappingDao.deleteByItemId(itemId);
        createItemLabelMappingService.create(itemId, labelIds);
    }
}
