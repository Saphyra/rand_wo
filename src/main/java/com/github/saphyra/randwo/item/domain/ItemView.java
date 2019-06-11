package com.github.saphyra.randwo.item.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemView {
    private UUID itemId;
    private List<UUID> labelIds;
    private Map<UUID, String> columns;
}
