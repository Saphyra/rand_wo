package com.github.saphyra.randwo.label.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class LabelViewConverterTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @InjectMocks
    private LabelViewConverter underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Test
    public void convert_deletable() {
        //GIVEN
        Label label = Label.builder()
            .labelId(LABEL_ID)
            .labelValue(LABEL_VALUE)
            .build();
        given(itemLabelMappingDao.getByLabelId(LABEL_ID)).willReturn(Arrays.asList(itemLabelMapping));
        given(itemLabelMapping.getItemId()).willReturn(ITEM_ID);
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping, itemLabelMapping));
        //WHEN
        LabelView result = underTest.convert(label);
        //THEN
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
        assertThat(result.getLabelValue()).isEqualTo(LABEL_VALUE);
        assertThat(result.getItems()).isEqualTo(1);
        assertThat(result.isDeletable()).isTrue();
    }

    @Test
    public void convert_notDeletable() {
        //GIVEN
        Label label = Label.builder()
            .labelId(LABEL_ID)
            .labelValue(LABEL_VALUE)
            .build();
        given(itemLabelMappingDao.getByLabelId(LABEL_ID)).willReturn(Arrays.asList(itemLabelMapping));
        given(itemLabelMapping.getItemId()).willReturn(ITEM_ID);
        given(itemLabelMappingDao.getByItemId(ITEM_ID)).willReturn(Arrays.asList(itemLabelMapping));
        //WHEN
        LabelView result = underTest.convert(label);
        //THEN
        assertThat(result.getLabelId()).isEqualTo(LABEL_ID);
        assertThat(result.getLabelValue()).isEqualTo(LABEL_VALUE);
        assertThat(result.getItems()).isEqualTo(1);
        assertThat(result.isDeletable()).isFalse();
    }
}