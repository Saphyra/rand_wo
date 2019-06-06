package com.github.saphyra.randwo.storedobject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreObjectRequest {
    private String key;
    private String value;
}
