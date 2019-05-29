package com.github.saphyra.randwo.item.service;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static com.github.saphyra.randwo.item.service.ItemRequestValidator.NULL_EXISTING_LABELS;
import static com.github.saphyra.randwo.item.service.ItemRequestValidator.NULL_NEW_LABELS;
import static com.github.saphyra.randwo.item.service.ItemRequestValidator.NULL_VALUES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;

@RunWith(MockitoJUnitRunner.class)
public class ItemRequestValidatorTest {
    private static final String KEY_1 = "key_1";
    private static final String KEY_2 = "key_2";
    private static final String VALUE = "value";
    private static final UUID EXISTING_LABEL_ID_1 = UUID.randomUUID();
    private static final UUID EXISTING_LABEL_ID_2 = UUID.randomUUID();
    private static final List<UUID> EXISTING_LABEL_IDS = Arrays.asList(EXISTING_LABEL_ID_1, EXISTING_LABEL_ID_2);

    @Mock
    private LabelDao labelDao;

    @InjectMocks
    private ItemRequestValidator underTest;

    @Mock
    private Label label;

    @Test
    public void validate_nullValues() {
        //GIVEN
        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(EXISTING_LABEL_IDS)
            .values(null)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.VALUE_IS_NULL.getErrorCode());
        assertThat(exception.getErrorMessage().getParams()).containsKey(PARAMETER_KEY_NULL_VALUE);
        assertThat(exception.getErrorMessage().getParams().get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(NULL_VALUES);
    }

    @Test
    public void validate_nullExistingLabels() {
        //GIVEN
        Map<String, String> values = new HashMap<>();
        values.put(KEY_1, VALUE);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(null)
            .values(values)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.VALUE_IS_NULL.getErrorCode());
        assertThat(exception.getErrorMessage().getParams()).containsKey(PARAMETER_KEY_NULL_VALUE);
        assertThat(exception.getErrorMessage().getParams().get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(NULL_EXISTING_LABELS);
    }

    @Test
    public void validate_nullNewLabels() {
        //GIVEN
        Map<String, String> values = new HashMap<>();
        values.put(KEY_1, VALUE);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(null)
            .existingLabels(EXISTING_LABEL_IDS)
            .values(values)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.VALUE_IS_NULL.getErrorCode());
        assertThat(exception.getErrorMessage().getParams()).containsKey(PARAMETER_KEY_NULL_VALUE);
        assertThat(exception.getErrorMessage().getParams().get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(NULL_NEW_LABELS);
    }

    @Test
    public void validate_noLabels() {
        //GIVEN
        Map<String, String> values = new HashMap<>();
        values.put(KEY_1, VALUE);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(Collections.emptyList())
            .values(values)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NO_LABELS.getErrorCode());
    }

    @Test
    public void validate_labelNotFound() {
        //GIVEN
        Map<String, String> values = new HashMap<>();
        values.put(KEY_1, VALUE);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(EXISTING_LABEL_IDS)
            .values(values)
            .build();
        given(labelDao.findById(EXISTING_LABEL_ID_1)).willReturn(Optional.of(label));
        given(labelDao.findById(EXISTING_LABEL_ID_2)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.LABEL_NOT_FOUND.getErrorCode());

        verify(labelDao).findById(EXISTING_LABEL_ID_1);
        verify(labelDao).findById(EXISTING_LABEL_ID_2);
    }

    @Test
    public void validate_noValues() {
        //GIVEN
        Map<String, String> values = new HashMap<>();

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(EXISTING_LABEL_IDS)
            .values(values)
            .build();
        given(labelDao.findById(any())).willReturn(Optional.of(label));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NO_ITEM_VALUES.getErrorCode());
    }

    @Test
    public void validate_nullValue() {
        //GIVEN
        Map<String, String> values = new HashMap<>();
        values.put(KEY_1, VALUE);
        values.put(KEY_2, null);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(EXISTING_LABEL_IDS)
            .values(values)
            .build();
        given(labelDao.findById(any())).willReturn(Optional.of(label));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(itemRequest));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_ITEM_VALUE.getErrorCode());
    }

    @Test
    public void validate_valid() {
        //GIVEN
        Map<String, String> values = new HashMap<>();
        values.put(KEY_1, VALUE);

        ItemRequest itemRequest = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabels(EXISTING_LABEL_IDS)
            .values(values)
            .build();
        given(labelDao.findById(any())).willReturn(Optional.of(label));
        //WHEN
        underTest.validate(itemRequest);
    }
}