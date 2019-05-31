package com.github.saphyra.randwo.mapping.itemvalue.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class ItemValueMappingFactoryTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String VALUE = "value";
    private static final UUID MAPPING_ID = UUID.randomUUID();

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private ItemValueMappingFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(MAPPING_ID);
        //WHEN
        ItemValueMapping result = underTest.create(ITEM_ID, KEY_ID, VALUE);
        //THEN
        assertThat(result.getMappingId()).isEqualTo(MAPPING_ID);
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }
}