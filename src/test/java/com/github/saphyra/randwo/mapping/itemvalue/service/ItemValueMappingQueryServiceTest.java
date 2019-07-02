package com.github.saphyra.randwo.mapping.itemvalue.service;

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
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ItemValueMappingQueryServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private ItemValueMappingQueryService underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Test
    public void findByItemIdAndKeyIdValidated_found() {
        //GIVEN
        given(itemValueMappingDao.findByItemIdAndKeyId(ITEM_ID, KEY_ID)).willReturn(Optional.of(itemValueMapping));
        //WHEN
        ItemValueMapping result = underTest.findByItemIdAndKeyIdValidated(ITEM_ID, KEY_ID);
        //THEN
        assertThat(result).isEqualTo(itemValueMapping);
    }

    @Test
    public void findByItemIdAndKeyIdValidated_notFound() {
        //GIVEN
        given(itemValueMappingDao.findByItemIdAndKeyId(ITEM_ID, KEY_ID)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByItemIdAndKeyIdValidated(ITEM_ID, KEY_ID));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.ITEM_VALUE_MAPPING_NOT_FOUND.getErrorCode());
    }
}