package com.github.saphyra.randwo.label.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.label.domain.Label;

@RunWith(MockitoJUnitRunner.class)
public class LabelConverterTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";

    @InjectMocks
    private LabelConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        LabelEntity entity = LabelEntity.builder()
            .labelId(LABEL_ID)
            .labelValue(LABEL_VALUE)
            .build();
        //WHEN
        Label result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
        assertThat(result.getLabelValue()).isEqualTo(LABEL_VALUE);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        Label label = Label.builder()
            .labelId(LABEL_ID)
            .labelValue(LABEL_VALUE)
            .build();
        //WHEN
        LabelEntity result = underTest.processDomainConversion(label);
        //THEN
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
        assertThat(result.getLabelValue()).isEqualTo(LABEL_VALUE);
    }
}