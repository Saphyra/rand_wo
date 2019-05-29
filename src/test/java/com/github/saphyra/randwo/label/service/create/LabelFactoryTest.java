package com.github.saphyra.randwo.label.service.create;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LabelFactoryTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";
    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private LabelFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(LABEL_ID);
        //WHEN
        Label label = underTest.create(LABEL_VALUE);
        //THEN
        assertThat(label.getLabelId()).isEqualTo(LABEL_ID);
        assertThat(label.getLabelValue()).isEqualTo(LABEL_VALUE);
    }
}