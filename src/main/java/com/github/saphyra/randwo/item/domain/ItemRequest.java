package com.github.saphyra.randwo.item.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequest {
    private Map<UUID, String> existingKeyValueIds;
    private Map<String, String> newKeyValues;
    private List<UUID> existingLabelIds;
    private List<String> newLabels;
}
