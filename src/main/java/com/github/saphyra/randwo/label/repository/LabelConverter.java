package com.github.saphyra.randwo.label.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.randwo.label.domain.Label;

@Component
public class LabelConverter extends ConverterBase<LabelEntity, Label> {
    @Override
    protected Label processEntityConversion(LabelEntity labelEntity) {
        return Label.builder()
            .labelId(labelEntity.getLabelId())
            .labelValue(labelEntity.getLabelValue())
            .build();
    }

    @Override
    protected LabelEntity processDomainConversion(Label label) {
        return LabelEntity.builder()
            .labelId(label.getLabelId())
            .labelValue(label.getLabelValue())
            .build();
    }
}
