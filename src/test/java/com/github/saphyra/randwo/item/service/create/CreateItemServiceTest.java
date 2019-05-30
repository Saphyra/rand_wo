package com.github.saphyra.randwo.item.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator;
import com.github.saphyra.randwo.mapping.service.create.MappingCreationService;


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
    private MappingCreationService mappingCreationService;

    @Mock
    private NewKeySaverService newKeySaverService;

    @Mock
    private NewLabelSaverService newLabelSaverService;

    @InjectMocks
    private CreateItemService underTest;

    @Mock
    private Item item;

    @Captor
    private ArgumentCaptor<List<UUID>> createMappingArgumentCaptor;

    @Captor
    private ArgumentCaptor<Map<UUID, String>> valuesArgumentCaptor;

    @Test
    public void createItem() {
        //GIVEN
        Map<String, String> newKeyValues = new HashMap<>();
        newKeyValues.put(KEY_VALUE, VALUE_1);

        Map<UUID, String> existingKeyValues = new HashMap<>();
        existingKeyValues.put(EXISTING_KEY_ID, VALUE_2);

        List<UUID> existingLabelIds = Arrays.asList(EXISTING_LABEL_ID);
        List<UUID> newLabelIds = Arrays.asList(NEW_LABEL_ID);
        List<String> newLabels = Arrays.asList(NEW_LABEL_VALUE);
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValues)
            .existingLabelIds(existingLabelIds)
            .newLabels(newLabels)
            .build();

        given(newLabelSaverService.saveLabels(newLabels)).willReturn(newLabelIds);

        Map<UUID, String> newKeyValuesResult = new HashMap<>();
        newKeyValuesResult.put(NEW_KEY_ID, VALUE_1);

        given(newKeySaverService.saveKeys(newKeyValues)).willReturn(newKeyValuesResult);

        given(itemFactory.create(anyMap())).willReturn(item);
        given(item.getItemId()).willReturn(ITEM_ID);
        //WHEN
        underTest.createItem(request);
        //THEN
        verify(itemRequestValidator).validate(request);
        verify(mappingCreationService).createMapping(eq(ITEM_ID), createMappingArgumentCaptor.capture());
        assertThat(createMappingArgumentCaptor.getValue()).containsOnly(EXISTING_LABEL_ID, NEW_LABEL_ID);

        verify(itemFactory).create(valuesArgumentCaptor.capture());
        assertThat(valuesArgumentCaptor.getValue().get(EXISTING_KEY_ID)).isEqualTo(VALUE_2);
        assertThat(valuesArgumentCaptor.getValue().get(NEW_KEY_ID)).isEqualTo(VALUE_1);

        verify(itemDao).save(item);
    }
}