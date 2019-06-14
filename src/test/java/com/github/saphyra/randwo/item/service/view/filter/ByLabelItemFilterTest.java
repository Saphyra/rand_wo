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
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ByLabelItemFilterTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final String LABEL_PART = "l_V";
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";
    private static final String NOT_LABEL_VALUE = "khkphb";

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private LabelQueryService labelQueryService;

    @InjectMocks
    private ByLabelItemFilter underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Mock
    private Label label;

    private Item item = Item.builder().itemId(ITEM_ID).build();

    @Test
    public void test_nullSearchByLabel_true() {
        //GIVEN
        GetItemsRequest request = new GetItemsRequest();
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void test_labelValueContains_true() {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByLabel(LABEL_PART)
            .build();

        given(itemLabelMapping.getLabelId()).willReturn(LABEL_ID);
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping));

        given(label.getLabelValue()).willReturn(LABEL_VALUE);
        given(labelQueryService.findByLabelIdValidated(LABEL_ID)).willReturn(label);
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void test_labelValueDoesNotContain_false() {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByLabel(LABEL_PART)
            .build();

        given(itemLabelMapping.getLabelId()).willReturn(LABEL_ID);
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping));

        given(label.getLabelValue()).willReturn(NOT_LABEL_VALUE);
        given(labelQueryService.findByLabelIdValidated(LABEL_ID)).willReturn(label);
        //WHEN
        boolean result = underTest.test(item, request);
        //THEN
        assertThat(result).isFalse();
    }
}