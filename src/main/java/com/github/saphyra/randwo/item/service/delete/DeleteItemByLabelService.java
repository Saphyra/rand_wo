package com.github.saphyra.randwo.item.service.delete;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.service.validator.DeleteItemRequestValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteItemByLabelService {
    private final DeleteItemRequestValidator deleteItemRequestValidator;
    private final List<ItemDeletionProcessor> itemDeletionProcessors;

    @Transactional
    public void deleteItems(DeleteItemRequest request) {
        deleteItemRequestValidator.validate(request);

        itemDeletionProcessors.stream()
            .filter(itemDeletionProcessor -> itemDeletionProcessor.canProcess(request.getItemDeleteMethod()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ItemDeletionProcessor not found for deletionMethod " + request.getItemDeleteMethod()))
            .process(request);
    }
}
