package com.github.saphyra.randwo.key.service.delete;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.UnprocessableEntityException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class KeyDeletionValidatorTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private KeyDeletionValidator underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Test
    public void validateKeyCanBeDeleted_itemWithOneKey() {
        //GIVEN
        given(itemValueMappingDao.getByKeyId(KEY_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getItemId()).willReturn(ITEM_ID);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validateKeyCanBeDeleted(KEY_ID));
        //THEN
        assertThat(ex).isInstanceOf(UnprocessableEntityException.class);
        UnprocessableEntityException exception = (UnprocessableEntityException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.ITEM_HAS_ONLY_ONE_KEY.getErrorCode());
    }

    @Test
    public void validateKeyCanBeDeleted() {
        //GIVEN
        given(itemValueMappingDao.getByKeyId(KEY_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getItemId()).willReturn(ITEM_ID);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping, itemValueMapping));
        //WHEN
        underTest.validateKeyCanBeDeleted(KEY_ID);
        //THEN
        verify(itemValueMappingDao).getByKeyId(KEY_ID);
        verify(itemValueMappingDao).getByItemId(ITEM_ID);
    }
}