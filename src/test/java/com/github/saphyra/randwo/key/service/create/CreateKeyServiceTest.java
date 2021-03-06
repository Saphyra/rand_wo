package com.github.saphyra.randwo.key.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.key.service.KeyValueValidator;

@RunWith(MockitoJUnitRunner.class)
public class CreateKeyServiceTest {
    private static final String KEY_VALUE = "key_value";
    private static final UUID NEW_KEY_ID = UUID.randomUUID();

    @Mock
    private KeyDao keyDao;

    @Mock
    private KeyFactory keyFactory;

    @Mock
    private KeyValueValidator keyValueValidator;

    @InjectMocks
    private CreateKeyService underTest;

    @Mock
    private Key key;

    @Test
    public void createKey() {
        //GIVEN
        given(keyFactory.create(KEY_VALUE)).willReturn(key);
        given(key.getKeyId()).willReturn(NEW_KEY_ID);
        //WHEN
        UUID result = underTest.createKey(KEY_VALUE);
        //THEN
        verify(keyValueValidator).validate(KEY_VALUE);
        verify(keyDao).save(key);
        assertThat(result).isEqualTo(NEW_KEY_ID);
    }
}