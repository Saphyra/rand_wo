package com.github.saphyra.randwo.mapping.itemvalue.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;

@Component
public class ItemValueMappingDao extends AbstractDao<ItemValueMappingEntity, ItemValueMapping, UUID, ItemValueMappingRepository> {
    public ItemValueMappingDao(ItemValueMappingConverter converter, ItemValueMappingRepository repository) {
        super(converter, repository);
    }

    public void deleteByItemId(UUID itemId) {
        repository.deleteByItemId(itemId);
    }

    public Optional<ItemValueMapping> findByItemIdAndKeyId(UUID itemId, UUID keyId) {
        return converter.convertEntity(repository.findByItemIdAndKeyId(itemId, keyId));
    }
}
