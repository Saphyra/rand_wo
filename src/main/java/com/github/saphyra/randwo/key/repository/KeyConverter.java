package com.github.saphyra.randwo.key.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.key.domain.Key;

@Component
public class KeyConverter extends ConverterBase<KeyEntity, Key> {
    @Override
    protected Key processEntityConversion(KeyEntity keyEntity) {
        return Key.builder()
            .keyId(keyEntity.getKeyId())
            .keyValue(keyEntity.getKeyValue())
            .build();
    }

    @Override
    protected KeyEntity processDomainConversion(Key key) {
        return KeyEntity.builder()
            .keyId(key.getKeyId())
            .keyValue(key.getKeyValue())
            .build();
    }
}
