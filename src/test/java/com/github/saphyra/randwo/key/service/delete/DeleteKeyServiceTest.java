package com.github.saphyra.randwo.key.service.delete;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class DeleteKeyServiceTest {
    private static final UUID KEY_ID = UUID.randomUUID();

    @Mock
    private CollectionValidator collectionValidator;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @Mock
    private KeyDao keyDao;

    @Mock
    private KeyDeletionValidator keyDeletionValidator;

    @InjectMocks
    private DeleteKeyService underTest;

    @Test
    public void deleteKeys() {
        //GIVEN
        List<UUID> keyIds = Arrays.asList(KEY_ID);
        //WHEN
        underTest.deleteKeys(keyIds);
        //THEN
        verify(collectionValidator).validateDoesNotContainNull(keyIds, ErrorCode.NULL_IN_KEY_IDS);
        verify(keyDeletionValidator).validateKeyCanBeDeleted(KEY_ID);
        verify(keyDao).deleteById(KEY_ID);
        verify(itemValueMappingDao).deleteByKeyId(KEY_ID);
    }
}