package com.github.saphyra.randwo.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;

@RunWith(MockitoJUnitRunner.class)
public class CollectionValidatorTest {
    @InjectMocks
    private CollectionValidator underTest;

    @Test
    public void validate_nullValue() {
        //GIVEN
        List<String> list = new ArrayList<>();
        list.add(null);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validateDoesNotContainNull(list, ErrorCode.EMPTY_KEY_VALUE));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.EMPTY_KEY_VALUE.getErrorCode());
    }
}