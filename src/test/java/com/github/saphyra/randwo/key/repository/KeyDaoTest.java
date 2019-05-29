package com.github.saphyra.randwo.key.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;

@RunWith(MockitoJUnitRunner.class)
public class KeyDaoTest {
    private static final String KEY_VALUE = "key_value";

    @Mock
    private KeyConverter keyConverter;

    @Mock
    private KeyRepository keyRepository;

    @InjectMocks
    private KeyDao underTest;

    @Mock
    private KeyEntity keyEntity;

    @Mock
    private Key key;

    @Test
    public void findByKeyValue() {
        //GIVEN
        Optional<KeyEntity> entityOptional = Optional.of(this.keyEntity);
        given(keyRepository.findByKeyValue(KEY_VALUE)).willReturn(entityOptional);
        given(keyConverter.convertEntity(entityOptional)).willReturn(Optional.of(key));
        //WHEN
        Optional<Key> result = underTest.findByKeyValue(KEY_VALUE);
        //THEN
        assertThat(result).contains(key);
    }
}