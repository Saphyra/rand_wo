package com.github.saphyra.randwo.label.repository;

import com.github.saphyra.randwo.label.domain.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LabelDaoTest {
    private static final String LABEL_VALUE = "label_value";

    @Mock
    private LabelConverter labelConverter;

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelDao underTest;

    @Mock
    private Label label;

    @Mock
    private LabelEntity labelEntity;

    @Test
    public void findByLabelValue() {
        //GIVEN
        Optional<LabelEntity> labelEntityOptional = Optional.of(this.labelEntity);
        given(labelRepository.findByLabelValue(LABEL_VALUE)).willReturn(labelEntityOptional);

        Optional<Label> labelOptional = Optional.of(this.label);
        given(labelConverter.convertEntity(labelEntityOptional)).willReturn(labelOptional);
        //WHEN
        Optional<Label> result = underTest.findByLabelValue(LABEL_VALUE);
        //THEN
        assertThat(result).contains(label);
    }
}