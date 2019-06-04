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

@RunWith(MockitoJUnitRunner.class)
public class LabelControllerTest {
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private DeleteLabelService deleteLabelService;

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
}