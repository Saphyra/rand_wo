package com.github.saphyra.randwo.label.service.delete;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.UnprocessableEntityException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class LabelDeletionValidator {
    private final ItemLabelMappingDao itemLabelMappingDao;

    void validateLabelCanBeDeleted(UUID labelId) {
        itemLabelMappingDao.getByLabelId(labelId).forEach(this::validateSize);
    }

    private void validateSize(ItemLabelMapping itemLabelMapping) {
        if (itemLabelMappingDao.getByItemId(itemLabelMapping.getItemId()).size() == 1) {
            throw new UnprocessableEntityException(new ErrorMessage(ErrorCode.ITEM_HAS_ONLY_ONE_LABEL.getErrorCode()), "Label with id " + itemLabelMapping.getLabelId() + " cannot be deleted because there are items with only this label.");
        }
    }
}
