package com.github.saphyra.randwo.item.service.delete;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;
import com.github.saphyra.randwo.item.service.validator.DeleteItemRequestValidator;

@RunWith(MockitoJUnitRunner.class)
public class DeleteItemByLabelServiceTest {
    @Mock
    private DeleteItemRequestValidator deleteItemRequestValidator;

    @Mock
    private ItemDeletionProcessor itemDeletionProcessor;

    private DeleteItemByLabelService underTest;

    private DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().itemDeleteMethod(ItemDeleteMethod.CONTAINS).build();

    @Before
    public void setUp() {
        underTest = new DeleteItemByLabelService(deleteItemRequestValidator, Arrays.asList(itemDeletionProcessor));
    }

    @Test(expected = IllegalStateException.class)
    public void deleteItems_processorNotFound() {
        //GIVEN
        given(itemDeletionProcessor.canProcess(ItemDeleteMethod.CONTAINS)).willReturn(false);
        //WHEN
        underTest.deleteItems(deleteItemRequest);
    }

    @Test
    public void deleteItems() {
        //GIVEN
        given(itemDeletionProcessor.canProcess(ItemDeleteMethod.CONTAINS)).willReturn(true);
        //WHEN
        underTest.deleteItems(deleteItemRequest);
        //THEN
        verify(deleteItemRequestValidator).validate(deleteItemRequest);
        verify(itemDeletionProcessor).process(deleteItemRequest);
    }
}