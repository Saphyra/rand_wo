package com.github.saphyra.randwo.mapping.itemvalue.service.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class CreateItemValueMappingServiceTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String VALUE = "value";

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @Mock
    private ItemValueMappingFactory itemValueMappingFactory;

    @InjectMocks
    private CreateItemValueMappingService underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Test
    public void create_alreadyExists() {
        //GIVEN
        given(itemValueMappingDao.findByItemIdAndKeyId(ITEM_ID, KEY_ID)).willReturn(Optional.of(itemValueMapping));

        Map<UUID, String> keyValues = new HashMap<>();
        keyValues.put(KEY_ID, VALUE);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.create(ITEM_ID, keyValues));
        //THEN
        assertThat(ex).isInstanceOf(ConflictException.class);
        ConflictException exception = (ConflictException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.ITEM_VALUE_MAPPING_ALREADY_EXISTS.getErrorCode());
    }

    @Test
    public void create() {
        //GIVEN
        Map<UUID, String> keyValues = new HashMap<>();
        keyValues.put(KEY_ID, VALUE);

        given(itemValueMappingDao.findByItemIdAndKeyId(ITEM_ID, KEY_ID)).willReturn(Optional.empty());
        given(itemValueMappingFactory.create(ITEM_ID, KEY_ID, VALUE)).willReturn(itemValueMapping);
        //WHEN
        underTest.create(ITEM_ID, keyValues);
        //THEN
        verify(itemValueMappingDao).save(itemValueMapping);
    }
}