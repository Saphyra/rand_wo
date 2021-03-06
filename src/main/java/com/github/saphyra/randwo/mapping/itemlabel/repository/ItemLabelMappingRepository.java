package com.github.saphyra.randwo.mapping.itemlabel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLabelMappingRepository extends JpaRepository<ItemLabelMappingEntity, UUID> {
    @Transactional
    void deleteByItemId(UUID itemId);

    @Transactional
    void deleteByLabelId(UUID labelId);

    Optional<ItemLabelMappingEntity> findByItemIdAndLabelId(UUID itemId, UUID labelId);

    List<ItemLabelMappingEntity> getByItemId(UUID itemId);

    List<ItemLabelMappingEntity> getByLabelId(UUID labelId);
}
