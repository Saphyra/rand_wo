package com.github.saphyra.randwo.label.service.create;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LabelValueValidatorTest {
    private static final String LABEL_VALUE = "label_value";
    @Mock
    private LabelDao labelDao;

    @InjectMocks
    private LabelValueValidator underTest;

    @Mock
    private Label label;

    @Test
    public void validate_emptyValue() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(""));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.EMPTY_LABEL_VALUE.getErrorCode());
    }

    @Test
    public void validate_existingLabel() {
        //GIVEN
        given(labelDao.findByLabelValue(LABEL_VALUE)).willReturn(Optional.of(label));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(LABEL_VALUE));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.LABEL_VALUE_ALREADY_EXISTS.getErrorCode());
        verify(labelDao).findByLabelValue(LABEL_VALUE);
    }

    @Test
    public void validate_valid() {
        //GIVEN
        given(labelDao.findByLabelValue(LABEL_VALUE)).willReturn(Optional.empty());
        //WHEN
        underTest.validate(LABEL_VALUE);
        verify(labelDao).findByLabelValue(LABEL_VALUE);
    }
}