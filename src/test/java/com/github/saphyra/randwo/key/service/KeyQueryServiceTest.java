package com.github.saphyra.randwo.key.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
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
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class KeyQueryServiceTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @Mock
    private KeyDao keyDao;

    @InjectMocks
    private KeyQueryService underTest;

    @Mock
    private Key key;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Mock
    private ItemValueMapping itemValueMapping;

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

    @Test
    public void getAll() {
        //GIVEN
        given(keyDao.getAll()).willReturn(Arrays.asList(key));
        //WHEN
        List<Key> result = underTest.getAll();
        //THEN
        assertThat(result).containsOnly(key);
    }

    @Test
    public void getKeysForLabels() {
        //GIVEN
        given(itemLabelMappingDao.getByLabelId(LABEL_ID)).willReturn(Arrays.asList(itemLabelMapping));
        given(itemLabelMapping.getItemId()).willReturn(ITEM_ID);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getKeyId()).willReturn(KEY_ID);
        given(keyDao.findById(KEY_ID)).willReturn(Optional.of(key));
        //WHEN
        List<Key> result = underTest.getKeysForLabels(Arrays.asList(LABEL_ID));
        //THEN
        assertThat(result).containsOnly(key);
    }
}