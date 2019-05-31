package com.github.saphyra.randwo.mapping.itemlabel.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class ItemLabelMappingFactoryTest {
    private static final UUID MAPPING_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();
    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private ItemLabelMappingFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(MAPPING_ID);
        //WHEN
        ItemLabelMapping result = underTest.create(ITEM_ID, LABEL_ID);
        //THEN
        assertThat(result.getMappingId()).isEqualTo(MAPPING_ID);
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
    }
}