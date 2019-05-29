package com.github.saphyra.randwo.item.repository;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item")
public class ItemEntity {
    @Id
    private UUID itemId;

    @Type(type = "text")
    @Column(name = "item_values")
    private String values;
}
