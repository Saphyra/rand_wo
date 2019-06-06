package com.github.saphyra.randwo.storedobject.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "stored_object")
public class StoredObjectEntity {
    @Id
    @Column(name = "stored_object_key")
    private String key;

    @Column(name = "stored_object_value")
    private String value;
}
