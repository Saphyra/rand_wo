package com.github.saphyra.randwo.key.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.key.domain.Key;

@Component
public class KeyDao extends AbstractDao<KeyEntity, Key, UUID, KeyRepository> {
    public KeyDao(KeyConverter converter, KeyRepository repository) {
        super(converter, repository);
    }

    public Optional<Key> findByKeyValue(String keyValue) {
        return converter.convertEntity(repository.findByKeyValue(keyValue));
    }

    public List<Key> getAll() {
        return converter.convertEntity(repository.findAll());
    }
}
