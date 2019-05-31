package com.github.saphyra.randwo.mapping.itemvalue.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.service.create.CreateItemValueMappingService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateItemValueMappingService {
    private final ItemValueMappingDao itemValueMappingDao;
    private final CreateItemValueMappingService createItemValueMappingService;

    public void update(UUID itemId, Map<UUID, String> keyValues) {
        itemValueMappingDao.deleteByItemId(itemId);
        createItemValueMappingService.create(itemId, keyValues);
    }
}
