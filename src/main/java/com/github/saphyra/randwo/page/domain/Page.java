package com.github.saphyra.randwo.page.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Page {
    @NonNull
    private final String name;

    @NonNull
    private final String templateLocation;

    public Page(String fileName) {
        this.name = fileName;
        this.templateLocation = String.format("%s/page.html", fileName);
    }
}
