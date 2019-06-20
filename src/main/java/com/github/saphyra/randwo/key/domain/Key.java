package com.github.saphyra.randwo.key.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
public class Key {
    private UUID keyId;
    private String keyValue;
}
