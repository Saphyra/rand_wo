package com.github.saphyra.randwo.item;

import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.service.create.CreateItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    @Mock
    private CreateItemService createItemService;

    @InjectMocks
    private ItemController underTest;

    @Mock
    private ItemRequest itemRequest;

    @Test
    public void createItem(){
        //WHEN
        underTest.createItem(itemRequest);
        //THEN
        verify(createItemService).createItem(itemRequest);
    }
}