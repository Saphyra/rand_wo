package com.github.saphyra.randwo.item.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {
}
