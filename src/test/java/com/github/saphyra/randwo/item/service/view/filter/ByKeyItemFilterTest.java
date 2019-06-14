package com.github.saphyra.randwo.item.service.view.filter;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ByKeyItemFilterTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final String KEY_PART = "y_V";
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";
    private static final String NOT_KEY_VALUE = "asdfgasfrg";

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @Mock
    private KeyQueryService keyQueryService;

    @InjectMocks
    private ByKeyItemFilter underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Mock
    private Key key;

    private Item item = Item.builder().itemId(ITEM_ID).build();

    @Test
    public void test_nullSearchValue_true() {
        //GIVEN
        GetItemsRequest request = new GetItemsRequest();
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void test_keyValueContains_true() {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByKey(KEY_PART)
            .build();
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getKeyId()).willReturn(KEY_ID);

        given(key.getKeyValue()).willReturn(KEY_VALUE);
        given(keyQueryService.findByKeyIdValidated(KEY_ID)).willReturn(key);
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void test_keyValueDoesNotContain_false() {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByKey(KEY_PART)
            .build();
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        given(itemValueMapping.getKeyId()).willReturn(KEY_ID);

        given(key.getKeyValue()).willReturn(NOT_KEY_VALUE);
        given(keyQueryService.findByKeyIdValidated(KEY_ID)).willReturn(key);
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isFalse();
    }
}