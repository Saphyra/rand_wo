package com.github.saphyra.randwo.item.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ItemViewConverterTest {
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String VALUE = "value";
    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private ItemViewConverter underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Test
    public void convert() {
        //GIVEN
        Item item = Item.builder()
            .itemId(ITEM_ID)
            .build();

        given(itemLabelMapping.getLabelId()).willReturn(LABEL_ID);
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping));

        given(itemValueMapping.getKeyId()).willReturn(KEY_ID);
        given(itemValueMapping.getValue()).willReturn(VALUE);
        given(itemValueMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemValueMapping));
        //WHEN
        ItemView result = underTest.convert(item);
        //THEN
        assertThat(result.getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.getLabelIds()).containsOnly(LABEL_ID);
        assertThat(result.getColumns().get(KEY_ID)).isEqualTo(VALUE);
    }
}