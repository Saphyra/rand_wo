package com.github.saphyra.randwo.mapping.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLabelMappingRepository extends JpaRepository<ItemLabelMappingEntity, UUID> {
    Optional<ItemLabelMappingEntity> findByItemIdAndLabelId(UUID itemId, UUID labelId);
}
