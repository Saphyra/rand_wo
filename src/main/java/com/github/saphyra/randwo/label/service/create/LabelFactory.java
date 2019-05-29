package com.github.saphyra.randwo.label.service.create;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LabelFactory {
    private final IdGenerator idGenerator;

    public Label create(String labelValue) {
        return Label.builder()
            .labelId(idGenerator.randomUUID())
            .labelValue(labelValue)
            .build();
    }
}
