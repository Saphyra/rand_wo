package com.github.saphyra.randwo.label;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.label.service.delete.DeleteLabelService;
import com.github.saphyra.randwo.label.service.update.UpdateLabelService;

@RunWith(MockitoJUnitRunner.class)
public class LabelControllerTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String NEW_VALUE = "new_value";

    @Mock
    private DeleteLabelService deleteLabelService;

    @Mock
    private UpdateLabelService updateLabelService;

    @InjectMocks
    private LabelController underTest;

    @Test
    public void deleteLabels() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID);
        //WHEN
        underTest.deleteLabels(labelIds);
        //THEN
        verify(deleteLabelService).deleteLabels(labelIds);
    }

    @Test
    public void updateLabel() {
        //WHEN
        underTest.updateLabel(LABEL_ID, NEW_VALUE);
        //THEN
        verify(updateLabelService).update(LABEL_ID, NEW_VALUE);
    }
}