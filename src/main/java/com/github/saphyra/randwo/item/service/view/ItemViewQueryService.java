package com.github.saphyra.randwo.item.service.view;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ItemViewQueryService {
    private final ItemDao itemDao;
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;
    private final ItemViewConverter itemViewConverter;
    private final KeyQueryService keyQueryService;
    private final LabelQueryService labelQueryService;

    public List<ItemView> getItems(GetItemsRequest request) {
        return itemDao.getAll().stream()
            .filter(item -> filterByLabel(item, request.getSearchByLabel()))
            .filter(item -> filterByKey(item, request.getSearchByKey()))
            .filter(item -> filterByValue(item, request.getSearchByValue()))
            .map(itemViewConverter::convert)
            .collect(Collectors.toList());
    }

    private boolean filterByLabel(Item item, String searchByLabel) {
        if (isEmpty(searchByLabel)) {
            return true;
        }

        return itemLabelMappingDao.getByItemId(item.getItemId()).stream()
            .map(itemLabelMapping -> labelQueryService.findByLabelIdValidated(itemLabelMapping.getLabelId()))
            .anyMatch(label -> label.getLabelValue().equalsIgnoreCase(searchByLabel));
    }

    private boolean filterByKey(Item item, String searchByKey) {
        if (isEmpty(searchByKey)) {
            return true;
        }

        return itemValueMappingDao.getByItemId(item.getItemId()).stream()
            .map(itemValueMapping -> keyQueryService.findByKeyIdValidated(itemValueMapping.getKeyId()))
            .anyMatch(key -> key.getKeyValue().equalsIgnoreCase(searchByKey));
    }

    private boolean filterByValue(Item item, String searchByValue) {
        if (isEmpty(searchByValue)) {
            return true;
        }

        return itemValueMappingDao.getByItemId(item.getItemId()).stream()
            .anyMatch(itemValueMapping -> itemValueMapping.getValue().equalsIgnoreCase(searchByValue));
    }
}
