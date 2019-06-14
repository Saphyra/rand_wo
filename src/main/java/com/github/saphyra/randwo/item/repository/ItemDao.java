package com.github.saphyra.randwo.item.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.item.domain.Item;

@Component
public class ItemDao extends AbstractDao<ItemEntity, Item, UUID, ItemRepository> {
    public ItemDao(ItemConverter converter, ItemRepository repository) {
        super(converter, repository);
    }

    public List<Item> getAll() {
        return converter.convertEntity(repository.findAll());
    }
}
