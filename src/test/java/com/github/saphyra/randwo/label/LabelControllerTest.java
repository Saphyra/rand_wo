package com.github.saphyra.randwo.label;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.label.service.delete.DeleteLabelService;
import com.github.saphyra.randwo.label.service.update.UpdateLabelService;
import com.github.saphyra.randwo.label.service.view.LabelViewQueryService;

@RunWith(MockitoJUnitRunner.class)
public class LabelControllerTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String NEW_VALUE = "new_value";

    @Mock
    private DeleteLabelService deleteLabelService;

    @Mock
    private LabelViewQueryService labelViewQueryService;

    @Mock
    private UpdateLabelService updateLabelService;

    @InjectMocks
    private LabelController underTest;

    @Mock
    private LabelView labelView;

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
    public void getLabel() {
        //GIVEN
        given(labelViewQueryService.findByLabelId(LABEL_ID)).willReturn(labelView);
        //WHEN
        LabelView result = underTest.getLabel(LABEL_ID);
        //THEN
        assertThat(result).isEqualTo(labelView);
    }

    @Test
    public void getLabels() {
        //GIVEN
        given(labelViewQueryService.getAll()).willReturn(Arrays.asList(labelView));
        //WHEN
        List<LabelView> result = underTest.getLabels();
        //THEN
        assertThat(result).containsOnly(labelView);
    }

    @Test
    public void updateLabel() {
        //WHEN
        underTest.updateLabel(LABEL_ID, NEW_VALUE);
        //THEN
        verify(updateLabelService).update(LABEL_ID, NEW_VALUE);
    }
}