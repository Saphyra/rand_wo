package com.github.saphyra.randwo.key.service.update;

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
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.key.service.KeyValueValidator;

@RunWith(MockitoJUnitRunner.class)
public class UpdateKeyServiceTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String NEW_KEY_VALUE = "new_key_value";
    @Mock
    private KeyDao keyDao;

    @Mock
    private KeyQueryService keyQueryService;

    @Mock
    private KeyValueValidator keyValueValidator;

    @InjectMocks
    private UpdateKeyService underTest;

    @Mock
    private Key key;

    @Test
    public void updateKey() {
        //GIVEN
        given(keyQueryService.findByKeyIdValidated(KEY_ID)).willReturn(key);
        //WHEN
        underTest.updateKey(KEY_ID, NEW_KEY_VALUE);
        //THEN
        verify(keyValueValidator).validate(NEW_KEY_VALUE);
        verify(key).setKeyValue(NEW_KEY_VALUE);
        verify(keyDao).save(key);
    }
}