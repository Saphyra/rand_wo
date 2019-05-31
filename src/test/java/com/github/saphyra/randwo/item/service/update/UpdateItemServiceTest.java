package com.github.saphyra.randwo.item.service.update;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.common.CollectionAggregator;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.ItemQueryService;
import com.github.saphyra.randwo.item.service.NewKeySaverService;
import com.github.saphyra.randwo.item.service.NewLabelSaverService;
import com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.itemlabel.service.UpdateItemLabelMappingService;
import com.github.saphyra.randwo.mapping.itemvalue.service.UpdateItemValueMappingService;

@RunWith(MockitoJUnitRunner.class)
public class UpdateItemServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final String NEW_LABEL = "new_label";
    private static final UUID NEW_LABEL_ID = UUID.randomUUID();
    private static final String NEW_KEY_VALUE = "new_key_value";
    private static final String NEW_VALUE = "new_value";
    private static final UUID NEW_KEY_ID = UUID.randomUUID();
    private static final UUID EXISTING_KEY_ID = UUID.randomUUID();
    private static final String EXISTING_VALUE = "existing_value";
    private static final UUID EXISTING_LABEL_ID = UUID.randomUUID();

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemRequestValidator itemRequestValidator;

    @Mock
    private ItemQueryService itemQueryService;

    @Mock
    private CollectionAggregator collectionAggregator;

    @Mock
    private NewKeySaverService newKeySaverService;

    @Mock
    private NewLabelSaverService newLabelSaverService;

    @Mock
    private UpdateItemLabelMappingService updateItemLabelMappingService;

    @Mock
    private UpdateItemValueMappingService updateItemValueMappingService;

    @InjectMocks
    private UpdateItemService underTest;

    @Mock
    private Map<UUID, String> aggregatedKeyValues;

    @Mock
    private List<UUID> aggregatedLabelIds;

    @Test
    public void updateItem() {
        //GIVEN
        List<String> newLabels = Arrays.asList(NEW_LABEL);
        List<UUID> newLabelIds = Arrays.asList(NEW_LABEL_ID);
        List<UUID> existingLabelIds = Arrays.asList(EXISTING_LABEL_ID);
        given(newLabelSaverService.saveLabels(newLabels)).willReturn(newLabelIds);

        Map<String, String> newKeyValues = new HashMap<>();
        newKeyValues.put(NEW_KEY_VALUE, NEW_VALUE);
        Map<UUID, String> newKeyValueIds = new HashMap<>();
        newKeyValueIds.put(NEW_KEY_ID, NEW_VALUE);
        given(newKeySaverService.saveKeys(newKeyValues)).willReturn(newKeyValueIds);

        Map<UUID, String> existingKeyValueIds = new HashMap<>();
        existingKeyValueIds.put(EXISTING_KEY_ID, EXISTING_VALUE);
        given(collectionAggregator.aggregate(existingKeyValueIds, newKeyValueIds)).willReturn(aggregatedKeyValues);

        given(collectionAggregator.aggregate(existingLabelIds, newLabelIds)).willReturn(aggregatedLabelIds);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(newLabels)
            .newKeyValues(newKeyValues)
            .existingLabelIds(existingLabelIds)
            .existingKeyValueIds(existingKeyValueIds)
            .build();

        Item item = Item.builder()
            .itemId(ITEM_ID)
            .build();
        given(itemQueryService.findByItemIdValidated(ITEM_ID)).willReturn(item);
        //WHEN
        underTest.updateItem(ITEM_ID, itemRequest);
        //THEN
        verify(itemRequestValidator).validate(itemRequest);
        verify(updateItemLabelMappingService).update(ITEM_ID, aggregatedLabelIds);
        verify(updateItemValueMappingService).update(ITEM_ID, aggregatedKeyValues);
        verify(itemDao).save(item);
    }
}