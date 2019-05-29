package com.github.saphyra.randwo.key.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;

@RunWith(MockitoJUnitRunner.class)
public class KeyValueValidatorTest {
    private static final String KEY_VALUE = "key_value";

    @Mock
    private KeyDao keyDao;

    @InjectMocks
    private KeyValueValidator underTest;

    @Mock
    private Key key;

    @Test
    public void validate_blank() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(" "));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.EMPTY_KEY_VALUE.getErrorCode());
    }

    @Test
    public void validate_alreadyExists() {
        //GIVEN
        given(keyDao.findByKeyValue(KEY_VALUE)).willReturn(Optional.of(key));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(KEY_VALUE));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.KEY_VALUE_ALREADY_EXISTS.getErrorCode());
    }

    @Test
    public void validate_valid() {
        //GIVEN
        given(keyDao.findByKeyValue(KEY_VALUE)).willReturn(Optional.empty());
        //WHEN
        underTest.validate(KEY_VALUE);
        //THEN
        verify(keyDao).findByKeyValue(KEY_VALUE);
    }
}