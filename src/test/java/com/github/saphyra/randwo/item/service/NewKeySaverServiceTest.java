package com.github.saphyra.randwo.item.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.service.NewKeySaverService;
import com.github.saphyra.randwo.key.service.create.CreateKeyService;

@RunWith(MockitoJUnitRunner.class)
public class NewKeySaverServiceTest {
    private static final String NEW_KEY_VALUE = "new_key_value";
    private static final String VALUE = "value";
    private static final UUID NEW_KEY_ID = UUID.randomUUID();

    @Mock
    private CreateKeyService createKeyService;

    @InjectMocks
    private NewKeySaverService underTest;

    @Test
    public void saveKeys() {
        //GIVEN
        Map<String, String> newKeyValues = new HashMap<>();
        newKeyValues.put(NEW_KEY_VALUE, VALUE);
        given(createKeyService.createKey(NEW_KEY_VALUE)).willReturn(NEW_KEY_ID);
        //WHEN
        Map<UUID, String> result = underTest.saveKeys(newKeyValues);
        //THEN
        assertThat(result.get(NEW_KEY_ID)).isEqualTo(VALUE);
    }
}