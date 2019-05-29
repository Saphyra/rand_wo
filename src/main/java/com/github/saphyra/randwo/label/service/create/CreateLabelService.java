package com.github.saphyra.randwo.label.service.create;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateLabelService {
    private final LabelDao labelDao;
    private final LabelFactory labelFactory;
    private final LabelValueValidator labelValueValidator;

    public UUID createLabel(String labelValue) {
        labelValueValidator.validate(labelValue);

        Label label = labelFactory.create(labelValue);
        labelDao.save(label);
        return label.getLabelId();
    }
}
