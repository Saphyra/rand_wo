package com.github.saphyra.randwo.key.service.view;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.domain.KeyView;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeyViewConverter {
    private final ItemValueMappingDao itemValueMappingDao;

    KeyView convert(Key key) {
        return KeyView.builder()
            .keyId(key.getKeyId())
            .keyValue(key.getKeyValue())
            .items(itemValueMappingDao.getByKeyId(key.getKeyId()).size())
            .deletable(isDeletable(key.getKeyId()))
            .build();
    }

    private boolean isDeletable(UUID keyId) {
        return itemValueMappingDao.getByKeyId(keyId).stream()
            .noneMatch(itemValueMapping -> itemValueMappingDao.getByItemId(itemValueMapping.getItemId()).size() == 1);
    }
}
