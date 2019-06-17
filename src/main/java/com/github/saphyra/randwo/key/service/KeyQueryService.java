package com.github.saphyra.randwo.key.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeyQueryService {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;
    private final KeyDao keyDao;

    public Key findByKeyIdValidated(UUID keyId) {
        return keyDao.findById(keyId)
            .orElseThrow(() -> new NotFoundException(new ErrorMessage(ErrorCode.KEY_NOT_FOUND.getErrorCode()), "Key not found with keyId " + keyId));
    }

    public List<Key> getAll() {
        return keyDao.getAll();
    }

    public List<Key> getKeysForLabels(List<UUID> labelIds) {
        return labelIds.stream()
            //Get item ids linked to labels
            .flatMap(labelId -> itemLabelMappingDao.getByLabelId(labelId).stream())
            .map(ItemLabelMapping::getItemId)
            .distinct()
            //Get key ids linked to items
            .flatMap(itemId -> itemValueMappingDao.getByItemId(itemId).stream())
            .map(ItemValueMapping::getKeyId)
            .distinct()
            //Get keys for keyIds
            .map(this::findByKeyIdValidated)
            .collect(Collectors.toList());
    }
}
