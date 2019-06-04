package com.github.saphyra.randwo.label.service;

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
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;

@RunWith(MockitoJUnitRunner.class)
public class LabelQueryServiceTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    @Mock
    private LabelDao labelDao;

    @InjectMocks
    private LabelQueryService underTest;

    @Mock
    private Label label;

    @Test
    public void findByLabelIdValidated_notFound() {
        //GIVEN
        given(labelDao.findById(LABEL_ID)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByLabelIdValidated(LABEL_ID));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.LABEL_NOT_FOUND.getErrorCode());
    }

    @Test
    public void findByLabelIdValidated_found() {
        //GIVEN
        given(labelDao.findById(LABEL_ID)).willReturn(Optional.of(label));
        //WHEN
        Label result = underTest.findByLabelIdValidated(LABEL_ID);
        //THEN
        assertThat(result).isEqualTo(label);
    }
}