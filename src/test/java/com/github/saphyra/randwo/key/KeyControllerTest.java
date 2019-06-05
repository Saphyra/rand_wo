package com.github.saphyra.randwo.key;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.service.delete.DeleteKeyService;

@RunWith(MockitoJUnitRunner.class)
public class KeyControllerTest {
    private static final UUID KEY_ID = UUID.randomUUID();

    @Mock
    private DeleteKeyService deleteKeyService;

    @InjectMocks
    private KeyController underTest;

    @Test
    public void deleteKeys() {
        //GIVEN
        List<UUID> keyIds = Arrays.asList(KEY_ID);
        //WHEN
        underTest.deleteKeys(keyIds);
        //THEN
        verify(deleteKeyService).deleteKeys(keyIds);
    }
}