package com.github.saphyra.randwo.key.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class KeyView {
    private UUID keyId;
    private String keyValue;
    private Integer items;
    private boolean deletable;
}
