package com.github.saphyra.randwo.item.service.validator.itemrequest;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.LabelValidator.NULL_EXISTING_LABEL_IDS;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.LabelValidator.NULL_IN_EXISTING_LABEL_IDS;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.LabelValidator.NULL_IN_NEW_LABELS;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.LabelValidator.NULL_NEW_LABELS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.saphyra.randwo.common.CollectionValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;

@RunWith(MockitoJUnitRunner.class)
public class LabelValidatorTest {
    private static final String NEW_LABEL = "new_label";
    private static final List<String> NEW_LABELS = Arrays.asList(NEW_LABEL);
    private static final UUID EXISTING_LABEL_ID = UUID.randomUUID();
    private static final List<UUID> EXISTING_LABEL_IDS = Arrays.asList(EXISTING_LABEL_ID);

    @Mock
    private CollectionValidator collectionValidator;

    @Mock
    private LabelDao labelDao;

    @InjectMocks
    private LabelValidator underTest;

    @Mock
    private Label label;

    @Test
    public void validate_existingLabelIdsIsNull() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(null, NEW_LABELS));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.VALUE_IS_NULL.getErrorCode());
        assertThat(exception.getErrorMessage().getParams().get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(NULL_EXISTING_LABEL_IDS);
    }

    @Test
    public void validate_newLabelsIsNull() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(EXISTING_LABEL_IDS, null));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.VALUE_IS_NULL.getErrorCode());
        assertThat(exception.getErrorMessage().getParams().get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(NULL_NEW_LABELS);
    }

    @Test
    public void validate_emptyLabels() {
        Throwable ex = catchThrowable(() -> underTest.validate(Collections.emptyList(), Collections.emptyList()));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NO_LABELS.getErrorCode());
    }

    @Test
    public void validate_labelNotExists() {
        //GIVEN
        given(labelDao.findById(EXISTING_LABEL_ID)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(EXISTING_LABEL_IDS, NEW_LABELS));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.LABEL_NOT_FOUND.getErrorCode());
        verify(collectionValidator).validateDoesNotContainNull(EXISTING_LABEL_IDS, NULL_IN_EXISTING_LABEL_IDS);
        verify(collectionValidator).validateDoesNotContainNull(NEW_LABELS, NULL_IN_NEW_LABELS);
    }

    @Test
    public void validate_valid() {
        //GIVEN
        given(labelDao.findById(EXISTING_LABEL_ID)).willReturn(Optional.of(label));
        //WHEN
        underTest.validate(EXISTING_LABEL_IDS, NEW_LABELS);
        //THEN
        verify(collectionValidator).validateDoesNotContainNull(EXISTING_LABEL_IDS, NULL_IN_EXISTING_LABEL_IDS);
        verify(collectionValidator).validateDoesNotContainNull(NEW_LABELS, NULL_IN_NEW_LABELS);
    }
}