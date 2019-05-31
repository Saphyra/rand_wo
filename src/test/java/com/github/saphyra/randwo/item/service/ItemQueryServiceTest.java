package com.github.saphyra.randwo.item.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.repository.ItemDao;

@RunWith(MockitoJUnitRunner.class)
public class ItemQueryServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private ItemQueryService underTest;

    @Mock
    private Item item;

    @Test
    public void findByItemIdValidated_notFound() {
        //GIVEN
        given(itemDao.findById(ITEM_ID)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByItemIdValidated(ITEM_ID));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.ITEM_NOT_FOUND.getErrorCode());
    }

    @Test
    public void findByItemIdValidated_found() {
        //GIVEN
        given(itemDao.findById(ITEM_ID)).willReturn(Optional.of(item));
        //WHEN
        Item result = underTest.findByItemIdValidated(ITEM_ID);
        //THEN
        assertThat(result).isEqualTo(item);
    }
}