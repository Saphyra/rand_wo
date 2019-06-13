package com.github.saphyra.randwo.item.domain;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomItemRequest {
    private List<UUID> labelIds;
    private List<UUID> keyIds;
}
