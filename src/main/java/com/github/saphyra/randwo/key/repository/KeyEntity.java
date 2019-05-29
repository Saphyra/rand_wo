package com.github.saphyra.randwo.key.repository;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "value_key")
@Entity
public class KeyEntity {
    @Id
    private UUID keyId;
    private String keyValue;
}
