package com.github.saphyra.randwo.mapping.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.mapping.domain.ItemLabelMapping;

@Component
public class ItemLabelMappingDao extends AbstractDao<ItemLabelMappingEntity, ItemLabelMapping, UUID, ItemLabelMappingRepository> {
    public ItemLabelMappingDao(ItemLabelMappingConverter converter, ItemLabelMappingRepository repository) {
        super(converter, repository);
    }

    public void deleteByItemId(UUID itemId) {
        repository.deleteByItemId(itemId);
    }

    public Optional<ItemLabelMapping> findByItemIdAndLabelId(UUID itemId, UUID labelId) {
        return converter.convertEntity(repository.findByItemIdAndLabelId(itemId, labelId));
    }
}
