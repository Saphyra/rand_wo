package com.github.saphyra.randwo.mapping.itemlabel.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class CreateItemLabelMappingServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemLabelMappingFactory itemLabelMappingFactory;

    @InjectMocks
    private CreateItemLabelMappingService underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Test
    public void createMapping_alreadyExists() {
        //GIVEN
        given(itemLabelMappingDao.findByItemIdAndLabelId(ITEM_ID, LABEL_ID)).willReturn(Optional.of(itemLabelMapping));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.create(ITEM_ID, Arrays.asList(LABEL_ID)));
        //THEN
        assertThat(ex).isInstanceOf(ConflictException.class);
        ConflictException exception = (ConflictException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.ITEM_LABEL_MAPPING_ALREADY_EXISTS.getErrorCode());
    }

    @Test
    public void createMapping() {
        //GIVEN
        given(itemLabelMappingDao.findByItemIdAndLabelId(ITEM_ID, LABEL_ID)).willReturn(Optional.empty());
        given(itemLabelMappingFactory.create(ITEM_ID, LABEL_ID)).willReturn(itemLabelMapping);
        //WHEN
        underTest.create(ITEM_ID, Arrays.asList(LABEL_ID));
        //THEN
        verify(itemLabelMappingDao).save(itemLabelMapping);
    }
}