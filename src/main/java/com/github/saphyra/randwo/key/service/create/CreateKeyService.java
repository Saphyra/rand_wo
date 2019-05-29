package com.github.saphyra.randwo.key.service.create;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateKeyService {
    private final KeyDao keyDao;
    private final KeyFactory keyFactory;
    private final KeyValueValidator keyValueValidator;

    public Key createKey(String keyValue) {
        keyValueValidator.validate(keyValue);

        Key key = keyFactory.create(keyValue);
        keyDao.save(key);
        return key;
    }
}
