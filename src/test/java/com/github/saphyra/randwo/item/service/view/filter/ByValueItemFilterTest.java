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
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ByValueItemFilterTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final String VALUE_PART = "Al";
    private static final String VALUE = "value";
    private static final String NOT_VALUE = "khőoih";

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private ByValueItemFilter underTest;

    @Mock
    private ItemValueMapping itemValueMapping;

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
    public void test_valueContains_true() {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByValue(VALUE_PART)
            .build();
        given(itemValueMapping.getValue()).willReturn(VALUE);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void test_valueDoesNotContain_false() {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByValue(NOT_VALUE)
            .build();
        given(itemValueMapping.getValue()).willReturn(VALUE);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isFalse();
    }
}