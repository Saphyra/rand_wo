package com.github.saphyra.randwo.item.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class ItemFactoryTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY = UUID.randomUUID();
    private static final String VALUE = "value";

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private ItemFactory underTest;

    @Test
    public void create() {
        //GIVEN
        Map<UUID, String> values = new HashMap<>();
        values.put(KEY, VALUE);

        given(idGenerator.randomUUID()).willReturn(ITEM_ID);
        //WHEN
        Item result = underTest.create(values);
        //THEN
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getValues()).isEqualTo(values);
    }
}