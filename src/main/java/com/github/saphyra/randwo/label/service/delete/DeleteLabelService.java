package com.github.saphyra.randwo.label.service.delete;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteLabelService {
    private final CollectionValidator collectionValidator;
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final LabelDao labelDao;
    private final LabelDeletionValidator labelDeletionValidator;

    @Transactional
    public void deleteLabels(List<UUID> labelIds) {
        collectionValidator.validateDoesNotContainNull(labelIds, ErrorCode.NULL_IN_LABEL_IDS);

        labelIds.forEach(this::deleteLabel);
    }

    private void deleteLabel(UUID labelId) {
        labelDeletionValidator.validateLabelCanBeDeleted(labelId);
        itemLabelMappingDao.deleteByLabelId(labelId);
        labelDao.deleteById(labelId);
    }
}
