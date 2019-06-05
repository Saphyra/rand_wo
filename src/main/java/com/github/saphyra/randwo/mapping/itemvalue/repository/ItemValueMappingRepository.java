package com.github.saphyra.randwo.mapping.itemvalue.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemValueMappingRepository extends JpaRepository<ItemValueMappingEntity, UUID> {
    @Transactional
    void deleteByItemId(UUID itemId);

    @Transactional
    void deleteByKeyId(UUID keyId);

    Optional<ItemValueMappingEntity> findByItemIdAndKeyId(UUID itemId, UUID keyId);

    List<ItemValueMappingEntity> getByItemId(UUID itemId);

    List<ItemValueMappingEntity> getByKeyId(UUID keyId);
}
