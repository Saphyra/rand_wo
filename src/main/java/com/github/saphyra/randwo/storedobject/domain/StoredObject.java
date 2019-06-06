package com.github.saphyra.randwo.storedobject.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class StoredObject {
    @NonNull
    private String key;
    private String value;
}
