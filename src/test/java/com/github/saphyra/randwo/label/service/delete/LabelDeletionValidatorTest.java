package com.github.saphyra.randwo.label.service.delete;

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
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class LabelDeletionValidatorTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @InjectMocks
    private LabelDeletionValidator underTest;

    @Test
    public void validateCanBeDeleted_onlyOneLabel() {
        //GIVEN
        ItemLabelMapping itemLabelMapping = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .labelId(LABEL_ID)
            .itemId(ITEM_ID)
            .build();
        given(itemLabelMappingDao.getByLabelId(LABEL_ID)).willReturn(Arrays.asList(itemLabelMapping));
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validateLabelCanBeDeleted(LABEL_ID));
        //THEN
        assertThat(ex).isInstanceOf(UnprocessableEntityException.class);
        UnprocessableEntityException exception = (UnprocessableEntityException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.ITEM_HAS_ONLY_ONE_LABEL.getErrorCode());
    }

    @Test
    public void validateCanBeDeletedl() {
        //GIVEN
        ItemLabelMapping itemLabelMapping = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .labelId(LABEL_ID)
            .itemId(ITEM_ID)
            .build();
        given(itemLabelMappingDao.getByLabelId(LABEL_ID)).willReturn(Arrays.asList(itemLabelMapping));
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping, itemLabelMapping));
        //WHEN
        underTest.validateLabelCanBeDeleted(LABEL_ID);
        //THEN
        verify(itemLabelMappingDao).getByItemId(ITEM_ID);
    }
}