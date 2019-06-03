package com.github.saphyra.randwo.item.service.delete;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ContainsItemDeletionProcessorTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @InjectMocks
    private ContainsItemDeletionProcessor underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping;

    @Test
    public void canProcess_containsAll() {
        //WHEN
        boolean result = underTest.canProcess(ItemDeleteMethod.CONTAINS_ALL);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void canProcess_contains() {
        //WHEN
        boolean result = underTest.canProcess(ItemDeleteMethod.CONTAINS);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void process() {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID))
            .build();

        given(itemLabelMappingDao.getByLabelId(LABEL_ID)).willReturn(Arrays.asList(itemLabelMapping));
        given(itemLabelMapping.getItemId()).willReturn(ITEM_ID);
        //WHEN
        underTest.process(request);
        //THEN
        verify(itemDao).deleteById(ITEM_ID);
        verify(itemValueMappingDao).deleteByItemId(ITEM_ID);
        verify(itemLabelMappingDao).delete(itemLabelMapping);
    }
}