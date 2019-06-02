package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.service.validator.DeleteItemRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
///todo unit test
public class DeleteItemByLabelService {
    private final DeleteItemRequestValidator deleteItemRequestValidator;
    private final List<ItemDeletionProcessor> itemDeletionProcessors;

    @Transactional
    public void deleteItems(DeleteItemRequest request) {
        deleteItemRequestValidator.validate(request);

        itemDeletionProcessors.stream()
            .filter(itemDeletionProcessor -> itemDeletionProcessor.canProcess(request.getItemDeleteMethod()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("ItemDeletionProcessor not found for deletionMethod " + request.getItemDeleteMethod()))
            .process(request);
    }
}
