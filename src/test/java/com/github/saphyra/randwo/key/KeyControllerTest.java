package com.github.saphyra.randwo.key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.key.service.delete.DeleteKeyService;
import com.github.saphyra.randwo.key.service.update.UpdateKeyService;

@RunWith(MockitoJUnitRunner.class)
public class KeyControllerTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String NEW_VALUE = "new_value";
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private DeleteKeyService deleteKeyService;

    @Mock
    private KeyQueryService keyQueryService;

    @Mock
    private UpdateKeyService updateKeyService;

    @InjectMocks
    private KeyController underTest;

    @Mock
    private Key key;

    @Test
    public void deleteKeys() {
        //GIVEN
        List<UUID> keyIds = Arrays.asList(KEY_ID);
        //WHEN
        underTest.deleteKeys(keyIds);
        //THEN
        verify(deleteKeyService).deleteKeys(keyIds);
    }

    @Test
    public void getKey() {
        //GIVEN
        given(keyQueryService.findByKeyIdValidated(KEY_ID)).willReturn(key);
        //WHEN
        Key result = underTest.getKey(KEY_ID);
        //THEN
        assertThat(result).isEqualTo(key);
    }

    @Test
    public void getKeys() {
        //GIVEN
        given(keyQueryService.getAll()).willReturn(Arrays.asList(key));
        //WHEN
        List<Key> result = underTest.getKeys();
        //THEN
        assertThat(result).containsOnly(key);
    }

    @Test
    public void getKeysForLabels() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID);
        given(keyQueryService.getKeysForLabels(labelIds)).willReturn(Arrays.asList(key));
        //WHEN
        List<Key> result = underTest.getKeysForLabels(labelIds);
        //THEN
        assertThat(result).containsOnly(key);
    }

    @Test
    public void updateKey() {
        //WHEN
        underTest.updateKey(NEW_VALUE, KEY_ID);
        //THEN
        verify(updateKeyService).updateKey(KEY_ID, NEW_VALUE);
    }
}