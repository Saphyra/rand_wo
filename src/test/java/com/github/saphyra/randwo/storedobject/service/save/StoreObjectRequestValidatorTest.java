package com.github.saphyra.randwo.storedobject.service.save;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;

@RunWith(MockitoJUnitRunner.class)
public class StoreObjectRequestValidatorTest {
    @InjectMocks
    private StoreObjectRequestValidator underTest;

    @Test
    public void validate_nullKey() {
        //GIVEN
        StoreObjectRequest request = StoreObjectRequest.builder()
            .key(null)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_OBJECT_KEY.getErrorCode());
    }

    @Test
    public void validate_blankKey() {
        //GIVEN
        StoreObjectRequest request = StoreObjectRequest.builder()
            .key("   ")
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.EMPTY_OBJECT_KEY.getErrorCode());
    }

    @Test
    public void validate_longKey() {
        //GIVEN
        byte[] array = new byte[300];
        new Random().nextBytes(array);
        String tooLongKey = new String(array, Charset.forName("UTF-8"));
        StoreObjectRequest request = StoreObjectRequest.builder()
            .key(tooLongKey)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.OBJECT_KEY_TOO_LONG.getErrorCode());
    }

    @Test
    public void validate_valid() {
        //GIVEN
        StoreObjectRequest request = StoreObjectRequest.builder()
            .key("ads")
            .build();
        //WHEN
        underTest.validate(request);
    }
}