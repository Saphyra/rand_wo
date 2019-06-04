package com.github.saphyra.randwo.key.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;

@RunWith(MockitoJUnitRunner.class)
public class KeyConverterTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";

    @InjectMocks
    private KeyConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        KeyEntity entity = KeyEntity.builder()
            .keyId(KEY_ID)
            .keyValue(KEY_VALUE)
            .build();
        //WHEN
        Key result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getKeyValue()).isEqualTo(KEY_VALUE);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        Key domain = Key.builder()
            .keyId(KEY_ID)
            .keyValue(KEY_VALUE)
            .build();
        //WHEN
        KeyEntity result = underTest.processDomainConversion(domain);
        //THEN
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getKeyValue()).isEqualTo(KEY_VALUE);
    }
}