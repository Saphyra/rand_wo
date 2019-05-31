package com.github.saphyra.randwo.item.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Item {
    @NonNull
    private final UUID itemId;

    @NonNull
    @Builder.Default
    private Map<UUID, String> values = new HashMap<>();
}
