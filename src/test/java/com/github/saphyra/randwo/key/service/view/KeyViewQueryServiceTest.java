package com.github.saphyra.randwo.key.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.domain.KeyView;
import com.github.saphyra.randwo.key.service.KeyQueryService;

@RunWith(MockitoJUnitRunner.class)
public class KeyViewQueryServiceTest {
    @Mock
    private KeyQueryService keyQueryService;

    @Mock
    private KeyViewConverter keyViewConverter;

    @InjectMocks
    private KeyViewQueryService underTest;

    @Mock
    private Key key;

    @Mock
    private KeyView keyView;

    @Test
    public void getAll() {
        //GIVEN
        given(keyQueryService.getAll()).willReturn(Arrays.asList(key));
        given(keyViewConverter.convert(key)).willReturn(keyView);
        //WHEN
        List<KeyView> result = underTest.getAll();
        //THEN
        assertThat(result).containsOnly(keyView);
    }
}