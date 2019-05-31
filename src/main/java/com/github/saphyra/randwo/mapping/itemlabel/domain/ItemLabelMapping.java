package com.github.saphyra.randwo.mapping.itemlabel.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemLabelMapping {
    @NonNull
    private final UUID mappingId;

    @NonNull
    private final UUID itemId;

    @NonNull
    private final UUID labelId;
}
