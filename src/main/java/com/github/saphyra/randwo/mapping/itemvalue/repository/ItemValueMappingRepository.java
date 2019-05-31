package com.github.saphyra.randwo.mapping.itemvalue.repository;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemValueMappingRepository extends JpaRepository<ItemValueMappingEntity, UUID> {
    @Transactional
    void deleteByItemId(UUID itemId);
    Optional<ItemValueMappingEntity> findByItemIdAndKeyId(UUID itemId, UUID keyId);
}
