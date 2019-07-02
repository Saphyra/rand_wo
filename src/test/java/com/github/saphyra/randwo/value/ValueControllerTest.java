package com.github.saphyra.randwo.value;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.value.domain.PlayValueRequest;
import com.github.saphyra.randwo.value.service.PlayValueService;

@RunWith(MockitoJUnitRunner.class)
public class ValueControllerTest {
    @Mock
    private PlayValueService playValueService;

    @InjectMocks
    private ValueController underTest;

    @Mock
    private PlayValueRequest playValueRequest;

    @Test
    public void playValue() {
        //WHEN
        underTest.playValue(playValueRequest);
        //THEN
        verify(playValueService).playValue(playValueRequest);
    }
}