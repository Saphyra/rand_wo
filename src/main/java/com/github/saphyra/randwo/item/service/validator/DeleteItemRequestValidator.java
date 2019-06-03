package com.github.saphyra.randwo.item.service.validator;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteItemRequestValidator {
    public static final String NULL_IN_LABEL_IDS = "null-in-label-ids";

    private final CollectionValidator collectionValidator;

    public void validate(DeleteItemRequest request) {
        if (isNull(request.getLabelIds())) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.NULL_LABEL_IDS.getErrorCode()), "LabelIds is null.");
        }

        collectionValidator.validateDoesNotContainNull(request.getLabelIds(), NULL_IN_LABEL_IDS);

        if(request.getLabelIds().isEmpty()){
            throw new BadRequestException(new ErrorMessage(ErrorCode.NO_LABELS.getErrorCode()), "LabelIds is empty.");
        }

        if (isNull(request.getItemDeleteMethod())) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.NULL_ITEM_DELETE_METHOD.getErrorCode()), "ItemDeleteMethod is null.");
        }
    }
}
