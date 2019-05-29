package com.github.saphyra.randwo.item.service.create;

import com.github.saphyra.randwo.label.service.create.CreateLabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class NewLabelSaverServiceTest {
    private static final String LABEL_VALUE = "label_value";
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private CreateLabelService createLabelService;

    @InjectMocks
    private NewLabelSaverService underTest;

    @Test
    public void saveLabels() {
        //GIVEN
        given(createLabelService.createLabel(LABEL_VALUE)).willReturn(LABEL_ID);
        //WHEN
        List<UUID> result = underTest.saveLabels(Arrays.asList(LABEL_VALUE));
        //THEN
        assertThat(result).containsExactly(LABEL_ID);
    }
}