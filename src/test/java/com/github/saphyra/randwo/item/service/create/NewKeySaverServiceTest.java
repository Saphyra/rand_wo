package com.github.saphyra.randwo.item.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.service.create.CreateKeyService;

@RunWith(MockitoJUnitRunner.class)
public class NewKeySaverServiceTest {
    private static final String NEW_KEY_VALUE = "new_key_value";

    @Mock
    private CreateKeyService createKeyService;

    @InjectMocks
    private NewKeySaverService underTest;

    @Mock
    private Key key;

    @Test
    public void saveKeys(){
        //GIVEN
        List<String> newKeyValues = Arrays.asList(NEW_KEY_VALUE);
        given(createKeyService.createKey(NEW_KEY_VALUE)).willReturn(key);
        //WHEN
        List<Key> result = underTest.saveKeys(newKeyValues);
        //THEN
        assertThat(result).containsOnly(key);
    }
}