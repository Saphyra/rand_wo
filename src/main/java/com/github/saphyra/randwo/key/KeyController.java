package com.github.saphyra.randwo.key;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.key.service.delete.DeleteKeyService;
import com.github.saphyra.randwo.key.service.update.UpdateKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KeyController {
    public static final String DELETE_KEYS_MAPPING = "/key";
    public static final String GET_KEY_MAPPING = "/key/{keyId}";
    public static final String GET_KEYS_MAPPING = "/key";
    public static final String GET_KEYS_FOR_LABELS_MAPPING = "/key/label";
    public static final String UPDATE_KEY_MAPPING = "/key/{keyId}";

    private final DeleteKeyService deleteKeyService;
    private final KeyQueryService keyQueryService;
    private final UpdateKeyService updateKeyService;

    @DeleteMapping(DELETE_KEYS_MAPPING)
    public void deleteKeys(@RequestBody List<UUID> keyIds) {
        log.info("Deleting keys {}", keyIds);
        deleteKeyService.deleteKeys(keyIds);
    }

    @GetMapping(GET_KEY_MAPPING)
    public Key getKey(@PathVariable("keyId") UUID keyId) {
        log.info("Querying key with id {}", keyId);
        return keyQueryService.findByKeyIdValidated(keyId);
    }

    @GetMapping(GET_KEYS_MAPPING)
    public List<Key> getKeys() {
        log.info("Querying all keys");
        return keyQueryService.getAll();
    }

    @PostMapping(GET_KEYS_FOR_LABELS_MAPPING)
    public List<Key> getKeysForLabels(@RequestBody List<UUID> labelIds){
        log.info("Querying keys for labelIds {}", labelIds);
        return keyQueryService.getKeysForLabels(labelIds);
    }

    @PostMapping(UPDATE_KEY_MAPPING)
    public void updateKey(
        @RequestBody String newValue,
        @PathVariable("keyId") UUID keyId
    ) {
        log.info("Updating key with id {} with value {}", keyId, newValue);
        updateKeyService.updateKey(keyId, newValue);
    }
}
