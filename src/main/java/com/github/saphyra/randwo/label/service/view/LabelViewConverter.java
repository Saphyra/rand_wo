package com.github.saphyra.randwo.label.service.view;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class LabelViewConverter {
    private final ItemLabelMappingDao itemLabelMappingDao;

    LabelView convert(Label label) {
        return LabelView.builder()
            .labelId(label.getLabelId())
            .labelValue(label.getLabelValue())
            .items(itemLabelMappingDao.getByLabelId(label.getLabelId()).size())
            .deletable(isDeletable(label))
            .build();
    }

    private boolean isDeletable(Label label) {
        return itemLabelMappingDao.getByLabelId(label.getLabelId()).stream()
            .noneMatch(itemLabelMapping -> itemLabelMappingDao.getByItemId(itemLabelMapping.getItemId()).size() == 1);
    }
}
