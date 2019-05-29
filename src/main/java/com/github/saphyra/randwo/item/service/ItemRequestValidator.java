package com.github.saphyra.randwo.item.service;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.label.repository.LabelDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemRequestValidator {
    public static final String NULL_VALUES = "values";
    public static final String NULL_EXISTING_LABELS = "existingLabels";
    public static final String NULL_NEW_LABELS = "newLabels";

    private final LabelDao labelDao;

    public void validate(ItemRequest request) {
        if (isNull(request.getValues())) {
            throw createNullException(NULL_VALUES);
        }

        if (isNull(request.getExistingLabels())) {
            throw createNullException(NULL_EXISTING_LABELS);
        }

        if (isNull(request.getNewLabels())) {
            throw createNullException(NULL_NEW_LABELS);
        }

        if (request.getExistingLabels().isEmpty() && request.getNewLabels().isEmpty()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.NO_LABELS.getErrorCode()), "New item should be ordered to at least one category.");
        }

        request.getExistingLabels().forEach(labelId -> {
            if (!labelDao.findById(labelId).isPresent()) {
                throw new BadRequestException(new ErrorMessage(ErrorCode.LABEL_NOT_FOUND.getErrorCode()), "Label not found with labelId " + labelId);
            }
        });

        if(request.getValues().isEmpty()){
            throw new BadRequestException(new ErrorMessage(ErrorCode.NO_ITEM_VALUES.getErrorCode()), "Item must contain at least one value.");
        }

        request.getValues().forEach((key, value) -> {
            if (isNull(value)) {
                throw new BadRequestException(new ErrorMessage(ErrorCode.NULL_ITEM_VALUE.getErrorCode()), String.format("Value of key %s is null.", key));
            }
        });
    }

    private BadRequestException createNullException(String nullValue) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(PARAMETER_KEY_NULL_VALUE, nullValue);
        ErrorMessage errorMessage = new ErrorMessage(ErrorCode.VALUE_IS_NULL.getErrorCode(), parameters);
        return new BadRequestException(errorMessage, String.format("%s is null.", nullValue));
    }
}
