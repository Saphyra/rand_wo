package com.github.saphyra.randwo.key.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<KeyEntity, UUID> {
    Optional<KeyEntity> findByKeyValue(String keyValue);
}
