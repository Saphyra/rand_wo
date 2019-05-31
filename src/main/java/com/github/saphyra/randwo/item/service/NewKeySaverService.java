package com.github.saphyra.randwo.item.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.key.service.create.CreateKeyService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewKeySaverService {
    private final CreateKeyService createKeyService;

    public Map<UUID, String> saveKeys(Map<String, String> newKeyValues) {

        Map<UUID, String> result = new HashMap<>();
        for (Map.Entry<String, String> newKeyValue : newKeyValues.entrySet()) {
            UUID newKeyId = createKeyService.createKey(newKeyValue.getKey());
            result.put(newKeyId, newKeyValue.getValue());
        }
        return result;
    }
}
