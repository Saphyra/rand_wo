package com.github.saphyra.randwo.label.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
public class LabelView {
    private UUID labelId;
    private String labelValue;
    private int items;
    private boolean deletable;
}
