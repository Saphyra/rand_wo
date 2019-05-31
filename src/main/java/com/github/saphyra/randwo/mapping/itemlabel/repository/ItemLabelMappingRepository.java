package com.github.saphyra.randwo.mapping.itemlabel.repository;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLabelMappingRepository extends JpaRepository<ItemLabelMappingEntity, UUID> {
    @Transactional
    void deleteByItemId(UUID itemId);

    Optional<ItemLabelMappingEntity> findByItemIdAndLabelId(UUID itemId, UUID labelId);
}
