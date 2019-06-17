package com.github.saphyra.randwo.label.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.label.service.LabelQueryService;

@RunWith(MockitoJUnitRunner.class)
public class LabelViewQueryServiceTest {
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private LabelQueryService labelQueryService;

    @Mock
    private LabelViewConverter labelViewConverter;

    @InjectMocks
    private LabelViewQueryService underTest;

    @Mock
    private Label label;

    @Mock
    private LabelView labelView;

    @Test
    public void findByLabelId() {
        //GIVEN
        given(labelQueryService.findByLabelIdValidated(LABEL_ID)).willReturn(label);
        given(labelViewConverter.convert(label)).willReturn(labelView);
        //WHEN
        LabelView result = underTest.findByLabelId(LABEL_ID);
        //THEN
        assertThat(result).isEqualTo(labelView);
    }

    @Test
    public void getAll() {
        //GIVEN
        given(labelQueryService.getAll()).willReturn(Arrays.asList(label));
        given(labelViewConverter.convert(label)).willReturn(labelView);
        //WHEN
        List<LabelView> result = underTest.getAll();
        //THEN
        assertThat(result).containsOnly(labelView);
    }
}