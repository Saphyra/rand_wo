package com.github.saphyra.randwo.mapping.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.mapping.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.service.create.MappingCreationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMappingService {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final MappingCreationService mappingCreationService;

    public void updateMappings(UUID itemId, List<UUID> labelIds) {
        itemLabelMappingDao.deleteByItemId(itemId);
        mappingCreationService.createMapping(itemId, labelIds);
    }
}
