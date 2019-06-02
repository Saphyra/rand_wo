package com.github.saphyra.randwo.item.service.delete;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteItemServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private CollectionValidator collectionValidator;

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private DeleteItemService underTest;

    @Test
    public void deleteItems_emptyItemIds() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.deleteItems(Collections.emptyList()));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.EMPTY_ITEM_IDS.getErrorCode());
    }

    @Test
    public void deleteItems() {
        //GIVEN
        List<UUID> itemIds = Arrays.asList(ITEM_ID);
        //WHEN
        underTest.deleteItems(itemIds);
        //THEN
        verify(collectionValidator).validateDoesNotContainNull(itemIds, ErrorCode.NULL_ITEM_ID.getErrorCode());
        verify(itemDao).deleteById(ITEM_ID);
        verify(itemLabelMappingDao).deleteByItemId(ITEM_ID);
        verify(itemValueMappingDao).deleteByItemId(ITEM_ID);
    }
}