package com.github.saphyra.randwo.key.service.delete;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteKeyService {
    private final CollectionValidator collectionValidator;
    private final ItemValueMappingDao itemValueMappingDao;
    private final KeyDao keyDao;
    private final KeyDeletionValidator keyDeletionValidator;

    public void deleteKeys(List<UUID> keyIds) {
        collectionValidator.validateDoesNotContainNull(keyIds, ErrorCode.NULL_IN_KEY_IDS);

        keyIds.forEach(this::deleteKey);
    }

    private void deleteKey(UUID keyId) {
        keyDeletionValidator.validateKeyCanBeDeleted(keyId);
        itemValueMappingDao.deleteByKeyId(keyId);
        keyDao.deleteById(keyId);
    }
}
