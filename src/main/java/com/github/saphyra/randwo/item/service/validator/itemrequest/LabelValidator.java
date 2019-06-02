package com.github.saphyra.randwo.item.service.validator.itemrequest;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.UUID;

import com.github.saphyra.randwo.common.CollectionValidator;
import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.common.ExceptionFactory;
import com.github.saphyra.randwo.label.repository.LabelDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LabelValidator {
    public static final String NULL_EXISTING_LABEL_IDS = "existingLabelIds";
    public static final String NULL_NEW_LABELS = "newLabels";
    public static final String NULL_IN_EXISTING_LABEL_IDS = "null-in-existing-label-ids";
    public static final String NULL_IN_NEW_LABELS = "null-in-new-labels";

    private final CollectionValidator collectionValidator;
    private final LabelDao labelDao;

    void validate(List<UUID> existingLabelIds, List<String> newLabels){
        if(isNull(existingLabelIds)){
            throw ExceptionFactory.createNullException(NULL_EXISTING_LABEL_IDS);
        }

        if(isNull(newLabels)){
            throw ExceptionFactory.createNullException(NULL_NEW_LABELS);
        }

        if(existingLabelIds.isEmpty() && newLabels.isEmpty()){
            throw new BadRequestException(new ErrorMessage(ErrorCode.NO_LABELS.getErrorCode()), "Item has no labels.");
        }

        collectionValidator.validateDoesNotContainNull(existingLabelIds, NULL_IN_EXISTING_LABEL_IDS);
        collectionValidator.validateDoesNotContainNull(newLabels, NULL_IN_NEW_LABELS);

        existingLabelIds.forEach(this::validateLabelExists);
    }

    private void validateLabelExists(UUID labelId) {
        if (!labelDao.findById(labelId).isPresent()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.LABEL_NOT_FOUND.getErrorCode()), "Label not found with labelId " + labelId);
        }
    }
}
