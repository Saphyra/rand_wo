package com.github.saphyra.randwo.item.service.delete;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class ContainsAllItemDeletionProcessorTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();

    @Mock
    private DeleteItemService deleteItemService;

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @InjectMocks
    private ContainsAllItemDeletionProcessor underTest;

    @Mock
    private ItemLabelMapping mapping1;

    @Mock
    private ItemLabelMapping mapping2;

    @Mock
    private ItemLabelMapping mapping3;

    @Test
    public void process() {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1, LABEL_ID_2))
            .build();

        given(mapping1.getItemId()).willReturn(ITEM_ID_1);
        given(mapping2.getItemId()).willReturn(ITEM_ID_1);
        given(mapping3.getItemId()).willReturn(ITEM_ID_2);

        given(itemLabelMappingDao.getByLabelId(LABEL_ID_1)).willReturn(Arrays.asList(mapping1, mapping2));
        given(itemLabelMappingDao.getByLabelId(LABEL_ID_2)).willReturn(Arrays.asList(mapping3));

        given(itemLabelMappingDao.findByItemIdAndLabelId(ITEM_ID_1, LABEL_ID_1)).willReturn(Optional.of(mapping1));
        given(itemLabelMappingDao.findByItemIdAndLabelId(ITEM_ID_1, LABEL_ID_2)).willReturn(Optional.of(mapping2));
        given(itemLabelMappingDao.findByItemIdAndLabelId(ITEM_ID_2, LABEL_ID_1)).willReturn(Optional.of(mapping1));
        given(itemLabelMappingDao.findByItemIdAndLabelId(ITEM_ID_2, LABEL_ID_2)).willReturn(Optional.empty());
        //WHEN
        underTest.process(request);
        //THEN
        verify(deleteItemService).delete(ITEM_ID_1);
    }
}