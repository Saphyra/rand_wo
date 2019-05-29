package com.github.saphyra.randwo.key.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class KeyFactoryTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";
    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private KeyFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(KEY_ID);
        //WHEN
        Key result = underTest.create(KEY_VALUE);
        //THEN
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getKeyValue()).isEqualTo(KEY_VALUE);
    }
}