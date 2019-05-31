package com.github.saphyra.randwo.mapping.itemvalue.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemValueMapping {
    @NonNull
    private final UUID mappingId;

    @NonNull
    private final UUID itemId;

    @NonNull
    private final UUID keyId;

    @NonNull
    private String value;
}
