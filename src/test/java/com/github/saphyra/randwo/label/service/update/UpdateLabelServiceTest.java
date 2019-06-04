package com.github.saphyra.randwo.label.service.update;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.label.service.LabelValueValidator;

@RunWith(MockitoJUnitRunner.class)
public class UpdateLabelServiceTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String NEW_VALUE = "new_value";

    @Mock
    private LabelDao labelDao;

    @Mock
    private LabelQueryService labelQueryService;

    @Mock
    private LabelValueValidator labelValueValidator;

    @InjectMocks
    private UpdateLabelService underTest;

    @Mock
    private Label label;

    @Test
    public void update() {
        //GIVEN
        given(labelQueryService.findByLabelIdValidated(LABEL_ID)).willReturn(label);
        //WHEN
        underTest.update(LABEL_ID, NEW_VALUE);
        //THEN
        verify(labelValueValidator).validate(NEW_VALUE);
        verify(label).setLabelValue(NEW_VALUE);
        verify(labelDao).save(label);
    }
}