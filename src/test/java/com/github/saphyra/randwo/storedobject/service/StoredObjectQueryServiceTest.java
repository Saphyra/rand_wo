package com.github.saphyra.randwo.storedobject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;

@RunWith(MockitoJUnitRunner.class)
public class StoredObjectQueryServiceTest {
    private static final String KEY = "key";
    @Mock
    private StoredObjectDao storedObjectDao;

    @InjectMocks
    private StoredObjectQueryService underTest;

    @Mock
    private StoredObject storedObject;

    @Test
    public void findByObjectKeyValidated_notFound() {
        //GIVEN
        given(storedObjectDao.findById(KEY)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByObjectKeyValidated(KEY));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.OBJECT_NOT_FOUND.getErrorCode());
    }

    @Test
    public void findByObjectKeyValidated_found() {
        //GIVEN
        given(storedObjectDao.findById(KEY)).willReturn(Optional.of(storedObject));
        //WHEN
        StoredObject result = underTest.findByObjectKeyValidated(KEY);
        //THEN
        assertThat(result).isEqualTo(storedObject);
    }
}