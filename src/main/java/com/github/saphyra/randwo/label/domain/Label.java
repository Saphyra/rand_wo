package com.github.saphyra.randwo.label.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Label {
    @NonNull
    private final UUID labelId;

    @NonNull
    private String labelValue;
}
