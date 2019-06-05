package com.github.saphyra.randwo.key.service.update;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.key.service.KeyValueValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateKeyService {
    private final KeyDao keyDao;
    private final KeyQueryService keyQueryService;
    private final KeyValueValidator keyValueValidator;

    public void updateKey(UUID keyId, String newValue) {
        keyValueValidator.validate(newValue);

        Key key = keyQueryService.findByKeyIdValidated(keyId);
        key.setKeyValue(newValue);
        keyDao.save(key);
    }
}
