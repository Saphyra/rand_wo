package com.github.saphyra.randwo.label.repository;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "label")
@Entity
public class LabelEntity {
    @Id
    private UUID labelId;

    @Type(type = "text")
    private String labelValue;
}
