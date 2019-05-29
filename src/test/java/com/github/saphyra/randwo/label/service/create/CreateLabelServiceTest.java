package com.github.saphyra.randwo.label.service.create;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateLabelServiceTest {
    private static final String LABEL_VALUE = "label_value";
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private LabelDao labelDao;

    @Mock
    private LabelFactory labelFactory;

    @Mock
    private LabelValueValidator labelValueValidator;

    @InjectMocks
    private CreateLabelService underTest;

    @Mock
    private Label label;

    @Test
    public void createLabel() {
        //GIVEN
        given(labelFactory.create(LABEL_VALUE)).willReturn(label);
        given(label.getLabelId()).willReturn(LABEL_ID);
        //WHEN
        UUID result = underTest.createLabel(LABEL_VALUE);
        //THEN
        verify(labelValueValidator).validate(LABEL_VALUE);
        verify(labelDao).save(label);
        assertThat(result).isEqualTo(LABEL_ID);
    }
}