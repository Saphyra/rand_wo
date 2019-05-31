package com.github.saphyra.randwo.mapping.itemlabel.repository;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_label_mapping")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemLabelMappingEntity {
    @Id
    private UUID mappingId;
    private UUID itemId;
    private UUID labelId;
}
