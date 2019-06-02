package com.github.saphyra.randwo.common;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;

import com.github.saphyra.randwo.common.CollectionValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;

@RunWith(MockitoJUnitRunner.class)
public class CollectionValidatorTest {
    private static final String NULL_VALUE = "null_value";
    @InjectMocks
    private CollectionValidator underTest;

    @Test
    public void validate_nullValue() {
        //GIVEN
        List<String> list = new ArrayList<>();
        list.add(null);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validateDoesNotContainNull(list, NULL_VALUE));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.VALUE_IS_NULL.getErrorCode());
        assertThat(exception.getErrorMessage().getParams()).containsKey(PARAMETER_KEY_NULL_VALUE);
        assertThat(exception.getErrorMessage().getParams().get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(NULL_VALUE);
    }
}