package com.github.saphyra.randwo.key.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;

@RunWith(MockitoJUnitRunner.class)
public class KeyQueryServiceTest {
    private static final UUID KEY_ID = UUID.randomUUID();

    @Mock
    private KeyDao keyDao;

    @InjectMocks
    private KeyQueryService underTest;

    @Mock
    private Key key;

    @Test
    public void findByKeyIdValidated_notFound() {
        //GIVEN
        given(keyDao.findById(KEY_ID)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByKeyIdValidated(KEY_ID));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.KEY_NOT_FOUND.getErrorCode());
    }

    @Test
    public void findByKeyIdValidated_found() {
        //GIVEN
        given(keyDao.findById(KEY_ID)).willReturn(Optional.of(key));
        //WHEN
        Key result = underTest.findByKeyIdValidated(KEY_ID);
        //THEN
        assertThat(result).isEqualTo(key);
    }
}