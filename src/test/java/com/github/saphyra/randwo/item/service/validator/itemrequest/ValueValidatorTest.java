package com.github.saphyra.randwo.item.service.validator.itemrequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;

@RunWith(MockitoJUnitRunner.class)
public class ValueValidatorTest {
    private static final String NEW_KEY_VALUE = "new_key_value";
    private static final String VALUE_1 = "value_1";
    private static final String VALUE_2 = "value_2";
    private static final UUID EXISTING_KEY_ID = UUID.randomUUID();

    @Mock
    private CollectionValidator collectionValidator;

    @Mock
    private KeyDao keyDao;

    @InjectMocks
    private ValueValidator underTest;

    private Map<String, String> newKeyValues;
    private Map<UUID, String> existingKeyValues;

    @Mock
    private Key key;

    @Before
    public void setUp() {
        newKeyValues = new HashMap<>();
        newKeyValues.put(NEW_KEY_VALUE, VALUE_1);

        existingKeyValues = new HashMap<>();
        existingKeyValues.put(EXISTING_KEY_ID, VALUE_2);
    }

    @Test
    public void validate_nullExistingKeyValues() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(null, newKeyValues));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_EXISTING_KEY_VALUE_IDS.getErrorCode());
    }

    @Test
    public void validate_nullNewKeyValues() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(existingKeyValues, null));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_NEW_KEY_VALUES.getErrorCode());
    }

    @Test
    public void validate_emptyValues() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(new HashMap<>(), new HashMap<>()));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NO_ITEM_VALUES.getErrorCode());
    }

    @Test
    public void validate_keyNotExists() {
        //GIVEN
        given(keyDao.findById(EXISTING_KEY_ID)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validate(existingKeyValues, newKeyValues));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.KEY_NOT_FOUND.getErrorCode());
        verify(collectionValidator).validateDoesNotContainNull(existingKeyValues.values(), ErrorCode.NULL_IN_EXISTING_KEY_VALUES);
        verify(collectionValidator).validateDoesNotContainNull(newKeyValues.values(), ErrorCode.NULL_IN_NEW_KEY_VALUES);
    }

    @Test
    public void validate_valid() {
        //GIVEN
        given(keyDao.findById(EXISTING_KEY_ID)).willReturn(Optional.of(key));
        //WHEN
        underTest.validate(existingKeyValues, newKeyValues);
        //THEN
        verify(collectionValidator).validateDoesNotContainNull(existingKeyValues.values(), ErrorCode.NULL_IN_EXISTING_KEY_VALUES);
        verify(collectionValidator).validateDoesNotContainNull(newKeyValues.values(), ErrorCode.NULL_IN_NEW_KEY_VALUES);
    }
}