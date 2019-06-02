package com.github.saphyra.randwo.mapping.itemlabel.repository;

import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemLabelMappingRepository extends JpaRepository<ItemLabelMappingEntity, UUID> {
    @Transactional
    void deleteByItemId(UUID itemId);

    Optional<ItemLabelMappingEntity> findByItemIdAndLabelId(UUID itemId, UUID labelId);

    List<ItemLabelMapping> getByLabelId(UUID labelId);
}
