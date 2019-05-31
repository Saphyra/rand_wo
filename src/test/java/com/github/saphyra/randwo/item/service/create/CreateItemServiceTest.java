package com.github.saphyra.randwo.item.service.create;

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
import com.github.saphyra.randwo.item.service.NewKeySaverService;
import com.github.saphyra.randwo.item.service.NewLabelSaverService;
import com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.itemlabel.service.create.CreateItemLabelMappingService;
import com.github.saphyra.randwo.mapping.itemvalue.service.create.CreateItemValueMappingService;


@RunWith(MockitoJUnitRunner.class)
public class CreateItemServiceTest {
    private static final String KEY_VALUE = "key_value";
    private static final String VALUE_1 = "value_1";
    private static final String VALUE_2 = "value_2";
    private static final UUID EXISTING_LABEL_ID = UUID.randomUUID();
    private static final String NEW_LABEL_VALUE = "new_label_value";
    private static final UUID NEW_LABEL_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID EXISTING_KEY_ID = UUID.randomUUID();
    private static final UUID NEW_KEY_ID = UUID.randomUUID();

    @Mock
    private ItemRequestValidator itemRequestValidator;

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemFactory itemFactory;

    @Mock
    private CollectionAggregator collectionAggregator;

    @Mock
    private CreateItemLabelMappingService createItemLabelMappingService;

    @Mock
    private  CreateItemValueMappingService createItemValueMappingService;

    @Mock
    private NewKeySaverService newKeySaverService;

    @Mock
    private NewLabelSaverService newLabelSaverService;

    @InjectMocks
    private CreateItemService underTest;

    @Mock
    private Item item;

    @Mock
    private Map<UUID, String> aggregatedKeyValueMapping;

    @Mock
    List<UUID> aggregatedLabelIds;

    @Test
    public void createItem() {
        //GIVEN
        Map<String, String> newKeyValues = new HashMap<>();
        newKeyValues.put(KEY_VALUE, VALUE_1);

        Map<UUID, String> existingKeyValueIds = new HashMap<>();
        existingKeyValueIds.put(EXISTING_KEY_ID, VALUE_2);

        List<UUID> existingLabelIds = Arrays.asList(EXISTING_LABEL_ID);
        List<UUID> newLabelIds = Arrays.asList(NEW_LABEL_ID);
        List<String> newLabels = Arrays.asList(NEW_LABEL_VALUE);
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .existingLabelIds(existingLabelIds)
            .newLabels(newLabels)
            .build();

        given(newLabelSaverService.saveLabels(newLabels)).willReturn(newLabelIds);

        Map<UUID, String> newKeyValuesResult = new HashMap<>();
        newKeyValuesResult.put(NEW_KEY_ID, VALUE_1);

        given(newKeySaverService.saveKeys(newKeyValues)).willReturn(newKeyValuesResult);

        given(collectionAggregator.aggregate(existingKeyValueIds, newKeyValuesResult)).willReturn(aggregatedKeyValueMapping);
        given(collectionAggregator.aggregate(existingLabelIds, newLabelIds)).willReturn(aggregatedLabelIds);

        given(itemFactory.create()).willReturn(item);
        given(item.getItemId()).willReturn(ITEM_ID);
        //WHEN
        underTest.createItem(request);
        //THEN
        verify(itemRequestValidator).validate(request);
        verify(createItemLabelMappingService).create(ITEM_ID, aggregatedLabelIds);
        verify(createItemValueMappingService).create(ITEM_ID, aggregatedKeyValueMapping);

        verify(itemDao).save(item);
    }
}