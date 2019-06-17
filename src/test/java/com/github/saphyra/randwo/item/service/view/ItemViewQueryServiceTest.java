package com.github.saphyra.randwo.item.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.view.filter.ItemFilter;

@RunWith(MockitoJUnitRunner.class)
public class ItemViewQueryServiceTest {
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemFilter itemFilter;

    @Mock
    private ItemViewConverter itemViewConverter;

    private ItemViewQueryService underTest;

    @Mock
    private ItemView itemView;

    @Before
    public void setUp() {
        underTest = new ItemViewQueryService(
            itemDao,
            Arrays.asList(itemFilter),
            itemViewConverter
        );
    }

    @Test
    public void getItems() {
        //GIVEN
        GetItemsRequest request = new GetItemsRequest();

        Item item1 = Item.builder().itemId(ITEM_ID_1).build();
        Item item2 = Item.builder().itemId(ITEM_ID_2).build();

        given(itemDao.getAll()).willReturn(Arrays.asList(item1, item2));
        given(itemFilter.test(item1, request)).willReturn(true);
        given(itemFilter.test(item2, request)).willReturn(false);

        given(itemViewConverter.convert(item1)).willReturn(itemView);
        //WHEN
        List<ItemView> result = underTest.getItems(request);
        //THEN
        assertThat(result).containsOnly(itemView);
    }
}