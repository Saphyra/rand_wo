package com.github.saphyra.randwo.item.service.validator.itemrequest;

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

import com.github.saphyra.randwo.item.domain.ItemRequest;

@RunWith(MockitoJUnitRunner.class)
public class ItemRequestValidatorTest {
    private static final String VALUE_1 = "value_1";
    private static final String VALUE_2 = "value_2";
    private static final UUID EXISTING_LABEL_ID = UUID.randomUUID();
    private static final String NEW_LABEL = "new_label";
    private static final UUID EXISTING_KEY_ID = UUID.randomUUID();
    private static final String NEW_KEY_VALUE = "new_key_value";

    @Mock
    private ValueValidator valueValidator;

    @Mock
    private LabelValidator labelValidator;

    @InjectMocks
    private ItemRequestValidator underTest;

    @Test
    public void validate() {
        //GIVEN
        List<UUID> existingLabelIds = Arrays.asList(EXISTING_LABEL_ID);
        List<String> newLabels = Arrays.asList(NEW_LABEL);
        Map<UUID, String> existingKeyValueIds = new HashMap<>();
        existingKeyValueIds.put(EXISTING_KEY_ID, VALUE_1);

        Map<String, String> newKeyValues = new HashMap<>();
        newKeyValues.put(NEW_KEY_VALUE, VALUE_2);

        ItemRequest itemRequest = ItemRequest.builder()
            .existingLabelIds(existingLabelIds)
            .newLabels(newLabels)
            .existingKeyValueIds(existingKeyValueIds)
            .newKeyValues(newKeyValues)
            .build();
        //WHEN
        underTest.validate(itemRequest);
        //THEN
        verify(valueValidator).validate(existingKeyValueIds, newKeyValues);
        verify(labelValidator).validate(existingLabelIds, newLabels);
    }
}