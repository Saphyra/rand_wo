package com.github.saphyra.randwo.key.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.domain.KeyView;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class KeyViewConverterTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private KeyViewConverter underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Test
    public void convert_deletable() {
        //GIVEN
        Key key = Key.builder()
            .keyId(KEY_ID)
            .keyValue(KEY_VALUE)
            .build();
        given(itemValueMappingDao.getByKeyId(KEY_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getItemId()).willReturn(ITEM_ID);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping, itemValueMapping));
        //WHEN
        KeyView result = underTest.convert(key);
        //THEN
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getKeyValue()).isEqualTo(KEY_VALUE);
        assertThat(result.getItems()).isEqualTo(1);
        assertThat(result.isDeletable()).isTrue();
    }

    @Test
    public void convert_notDeletable() {
        //GIVEN
        Key key = Key.builder()
            .keyId(KEY_ID)
            .keyValue(KEY_VALUE)
            .build();
        given(itemValueMappingDao.getByKeyId(KEY_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getItemId()).willReturn(ITEM_ID);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        //WHEN
        KeyView result = underTest.convert(key);
        //THEN
        assertThat(result.getKeyId()).isEqualTo(KEY_ID);
        assertThat(result.getKeyValue()).isEqualTo(KEY_VALUE);
        assertThat(result.getItems()).isEqualTo(1);
        assertThat(result.isDeletable()).isFalse();
    }
}