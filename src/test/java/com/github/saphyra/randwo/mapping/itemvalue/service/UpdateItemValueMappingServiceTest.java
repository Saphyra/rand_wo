package com.github.saphyra.randwo.mapping.itemvalue.service;

import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.service.create.CreateItemValueMappingService;

@RunWith(MockitoJUnitRunner.class)
public class UpdateItemValueMappingServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @Mock
    private CreateItemValueMappingService createItemValueMappingService;

    @InjectMocks
    private UpdateItemValueMappingService underTest;

    @Mock
    private Map<UUID, String> keyValues;

    @Test
    public void update() {
        //WHEN
        underTest.update(ITEM_ID, keyValues);
        //THEN
        verify(itemValueMappingDao).deleteByItemId(ITEM_ID);
        verify(createItemValueMappingService).create(ITEM_ID, keyValues);
    }
}