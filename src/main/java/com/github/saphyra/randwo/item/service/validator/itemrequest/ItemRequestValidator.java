package com.github.saphyra.randwo.item.service.validator.itemrequest;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.item.domain.ItemRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemRequestValidator {
    private final ValueValidator valueValidator;
    private final LabelValidator labelValidator;

    public void validate(ItemRequest request) {
        valueValidator.validate(request.getExistingKeyValueIds(), request.getNewKeyValues());
        labelValidator.validate(request.getExistingLabelIds(), request.getNewLabels());
    }
}
