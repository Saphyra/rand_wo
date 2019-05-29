package com.github.saphyra.randwo.item.service.create;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.service.create.MappingCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateItemService {
    private final ItemRequestValidator itemRequestValidator;
    private final ItemDao itemDao;
    private final ItemFactory itemFactory;
    private final MappingCreationService mappingCreationService;
    private final NewLabelSaverService newLabelSaverService;

    @Transactional
    public void createItem(ItemRequest itemRequest) {
        itemRequestValidator.validate(itemRequest);

        List<UUID> newLabelIds = newLabelSaverService.saveLabels(itemRequest.getNewLabels());

        Item item = itemFactory.create(itemRequest.getValues());

        mappingCreationService.createMapping(
            item.getItemId(),
            Stream.concat(itemRequest.getExistingLabels().stream(), newLabelIds.stream())
                .collect(Collectors.toList())
        );
        itemDao.save(item);
    }
}
