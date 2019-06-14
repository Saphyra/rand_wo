package com.github.saphyra.randwo.item.service.view.filter;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ByLabelItemFilter implements ItemFilter {
    private final ItemLabelMappingDao itemLabelMappingDao;
    private final LabelQueryService labelQueryService;

    @Override
    public boolean test(Item item, GetItemsRequest request) {
        String searchByLabel = request.getSearchByLabel();
        if (isEmpty(searchByLabel)) {
            return true;
        }

        return itemLabelMappingDao.getByItemId(item.getItemId()).stream()
            .map(itemLabelMapping -> labelQueryService.findByLabelIdValidated(itemLabelMapping.getLabelId()))
            .anyMatch(label -> label.getLabelValue().toLowerCase().contains(searchByLabel.toLowerCase()));
    }
}
