package com.github.saphyra.randwo.mapping.itemlabel.repository;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    ///todo unit test
    public List<ItemLabelMapping> getByLabelId(UUID labelId) {
        return repository.getByLabelId(labelId);
    }
}
