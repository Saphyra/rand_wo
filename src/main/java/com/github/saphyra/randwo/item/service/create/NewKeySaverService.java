package com.github.saphyra.randwo.item.service.create;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.service.create.CreateKeyService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class NewKeySaverService {
    private final CreateKeyService createKeyService;

    List<Key> saveKeys(Collection<String> newKeyValues) {
        return newKeyValues.stream()
            .map(createKeyService::createKey)
            .collect(Collectors.toList());
    }
}
