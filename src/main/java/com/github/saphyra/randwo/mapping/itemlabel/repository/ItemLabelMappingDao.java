package com.github.saphyra.randwo.mapping.itemlabel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;


@Component
public class ItemLabelMappingDao extends AbstractDao<ItemLabelMappingEntity, ItemLabelMapping, UUID, ItemLabelMappingRepository> {
    public ItemLabelMappingDao(ItemLabelMappingConverter converter, ItemLabelMappingRepository repository) {
        super(converter, repository);
    }

    public void deleteByItemId(UUID itemId) {
        repository.deleteByItemId(itemId);
    }

    public void deleteByLabelId(UUID labelId) {
        repository.deleteByLabelId(labelId);
    }

    public Optional<ItemLabelMapping> findByItemIdAndLabelId(UUID itemId, UUID labelId) {
        return converter.convertEntity(repository.findByItemIdAndLabelId(itemId, labelId));
    }

    public List<ItemLabelMapping> getByItemId(UUID itemId) {
        return converter.convertEntity(repository.getByItemId(itemId));
    }

    public List<ItemLabelMapping> getByLabelId(UUID labelId) {
        return converter.convertEntity(repository.getByLabelId(labelId));
    }
}
