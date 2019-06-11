package com.github.saphyra.randwo.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetItemsRequest {
    private String searchByLabel;
    private String searchByKey;
    private String searchByValue;
}
