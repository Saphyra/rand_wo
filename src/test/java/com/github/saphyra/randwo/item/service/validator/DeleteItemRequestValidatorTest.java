package com.github.saphyra.randwo.item.service.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;

@RunWith(MockitoJUnitRunner.class)
public class DeleteItemRequestValidatorTest {
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private CollectionValidator collectionValidator;

    @InjectMocks
    private DeleteItemRequestValidator underTest;

    @Test
    public void validate_nullLabelIds() {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(null)
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_LABEL_IDS.getErrorCode());
    }

    @Test
    public void validate_emptyLabelIds() {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Collections.emptyList())
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NO_LABELS.getErrorCode());
    }

    @Test
    public void validate_nullItemDeleteMethod() {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID))
            .itemDeleteMethod(null)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_ITEM_DELETE_METHOD.getErrorCode());
    }

    @Test
    public void validate_valid() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID);
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(labelIds)
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        underTest.validate(request);
        //THEN
        verify(collectionValidator).validateDoesNotContainNull(labelIds, ErrorCode.NULL_IN_LABEL_IDS);
    }
}