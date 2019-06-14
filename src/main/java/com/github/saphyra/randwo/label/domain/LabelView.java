package com.github.saphyra.randwo.label.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class LabelView {
    private UUID labelId;
    private String labelValue;
    private int items;
}
