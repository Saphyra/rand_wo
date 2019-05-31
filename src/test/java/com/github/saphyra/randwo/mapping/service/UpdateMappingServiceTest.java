package com.github.saphyra.randwo.mapping.service;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.service.create.MappingCreationService;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMappingServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final List<UUID> LABEL_IDS = Arrays.asList(LABEL_ID);

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private MappingCreationService mappingCreationService;

    @InjectMocks
    private UpdateMappingService underTest;

    @Test
    public void updateMappings() {
        //WHEN
        underTest.updateMappings(ITEM_ID, LABEL_IDS);
        //THEN
        verify(itemLabelMappingDao).deleteByItemId(ITEM_ID);
        verify(mappingCreationService).createMapping(ITEM_ID, LABEL_IDS);
    }
}