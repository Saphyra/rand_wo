package com.github.saphyra.randwo.mapping.itemvalue.repository;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_value_mapping")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ItemValueMappingEntity {
    @Id
    private UUID mappingId;

    private UUID itemId;
    private UUID keyId;
    private String value;
}
