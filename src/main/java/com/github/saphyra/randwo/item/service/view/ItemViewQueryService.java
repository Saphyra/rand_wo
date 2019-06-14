package com.github.saphyra.randwo.item.service.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.item.service.view.filter.ItemFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ItemViewQueryService {
    private final ItemDao itemDao;
    private final List<ItemFilter> itemFilters;
    private final ItemViewConverter itemViewConverter;

    public List<ItemView> getItems(GetItemsRequest request) {
        return itemDao.getAll().stream()
            .filter(item -> itemFilters.stream().allMatch(itemFilter -> itemFilter.test(item, request)))
            .map(itemViewConverter::convert)
            .collect(Collectors.toList());
    }
}
