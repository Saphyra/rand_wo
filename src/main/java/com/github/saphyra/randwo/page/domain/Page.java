package com.github.saphyra.randwo.page.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Page {
    @NonNull
    private final String name;

    public Page(String fileName) {
        this.name = fileName;
    }
}
