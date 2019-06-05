package com.github.saphyra.randwo.key;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.key.service.delete.DeleteKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KeyController {
    public static final String DELETE_KEYS_MAPPING = "/key";

    private final DeleteKeyService deleteKeyService;

    @DeleteMapping(DELETE_KEYS_MAPPING)
    public void deleteKeys(@RequestBody List<UUID> keyIds) {
        log.info("Deleting keys {}", keyIds);
        deleteKeyService.deleteKeys(keyIds);
    }
}
