package com.github.saphyra.randwo.mapping.itemlabel.service;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemlabel.service.create.CreateItemLabelMappingService;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMappingServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final List<UUID> LABEL_IDS = Arrays.asList(LABEL_ID);

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private CreateItemLabelMappingService createItemLabelMappingService;

    @InjectMocks
    private UpdateItemLabelMappingService underTest;

    @Test
    public void updateMappings() {
        //WHEN
        underTest.update(ITEM_ID, LABEL_IDS);
        //THEN
        verify(itemLabelMappingDao).deleteByItemId(ITEM_ID);
        verify(createItemLabelMappingService).create(ITEM_ID, LABEL_IDS);
    }
}