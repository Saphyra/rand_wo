package com.github.saphyra.randwo.key;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.key.service.delete.DeleteKeyService;
import com.github.saphyra.randwo.key.service.update.UpdateKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KeyController {
    public static final String DELETE_KEYS_MAPPING = "/key";
    public static final String UPDATE_KEY_MAPPING = "/key/{keyId}";

    private final DeleteKeyService deleteKeyService;
    private final UpdateKeyService updateKeyService;

    @DeleteMapping(DELETE_KEYS_MAPPING)
    public void deleteKeys(@RequestBody List<UUID> keyIds) {
        log.info("Deleting keys {}", keyIds);
        deleteKeyService.deleteKeys(keyIds);
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
