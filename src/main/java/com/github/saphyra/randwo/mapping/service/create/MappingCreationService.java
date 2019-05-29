package com.github.saphyra.randwo.mapping.service.create;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MappingCreationService {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final MappingFactory mappingFactory;

    public void createMapping(UUID itemId, List<UUID> labelIds) {
        labelIds.forEach(labelId -> createMapping(itemId, labelId));
    }

    private void createMapping(UUID itemId, UUID labelId) {
        if (itemLabelMappingDao.findByItemIdAndLabelId(itemId, labelId).isPresent()) {
            throw new ConflictException(
                new ErrorMessage(ErrorCode.MAPPING_ALREADY_EXISTS.getErrorCode()),
                String.format("Mapping already exists between item %s and label %s", itemId, labelId)
            );
        }

        ItemLabelMapping mapping = mappingFactory.create(itemId, labelId);
        itemLabelMappingDao.save(mapping);
    }
}
