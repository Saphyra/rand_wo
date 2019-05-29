package com.github.saphyra.randwo.key.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class Key {
    private final UUID keyId;
    private String keyValue;
}
