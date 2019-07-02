package com.github.saphyra.randwo.value.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.common.Speaker;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.service.ItemValueMappingQueryService;
import com.github.saphyra.randwo.value.domain.PlayValueRequest;

@RunWith(MockitoJUnitRunner.class)
public class PlayValueServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String VALUE = "value";

    @Mock
    private ItemValueMappingQueryService itemValueMappingQueryService;

    @Mock
    private Speaker speaker;

    @InjectMocks
    private PlayValueService underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Test
    public void playValue() {
        //GIVEN
        PlayValueRequest request = PlayValueRequest.builder()
            .itemId(ITEM_ID)
            .keyId(KEY_ID)
            .build();

        given(itemValueMappingQueryService.findByItemIdAndKeyIdValidated(ITEM_ID, KEY_ID)).willReturn(itemValueMapping);
        given(itemValueMapping.getValue()).willReturn(VALUE);
        //WHEN
        underTest.playValue(request);
        //THEN
        verify(speaker).speakValue(VALUE);
    }
}