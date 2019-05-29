package com.github.saphyra.randwo.key.service.create;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
class KeyFactory {
    private final IdGenerator idGenerator;

    Key create(String keyValue) {
        return Key.builder()
            .keyId(idGenerator.randomUUID())
            .keyValue(keyValue)
            .build();
    }
}
