package com.github.saphyra.randwo.label.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LabelRepository extends JpaRepository<LabelEntity, UUID> {
    Optional<LabelEntity> findByLabelValue(String labelValue);
}
