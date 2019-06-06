package com.github.saphyra.randwo.storedobject.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StoredObject {
    @NonNull
    private final String key;
    private String value;
}
