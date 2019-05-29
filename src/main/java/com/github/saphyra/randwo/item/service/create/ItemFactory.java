package com.github.saphyra.randwo.item.service.create;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemFactory {
    private final IdGenerator idGenerator;

    public Item create(Map<UUID, String> values) {
        return Item.builder()
            .itemId(idGenerator.randomUUID())
            .values(values)
            .build();
    }
}
