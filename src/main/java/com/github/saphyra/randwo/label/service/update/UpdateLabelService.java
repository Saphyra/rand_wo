package com.github.saphyra.randwo.label.service.update;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.label.service.LabelValueValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateLabelService {
    private final LabelDao labelDao;
    private final LabelQueryService labelQueryService;
    private final LabelValueValidator labelValueValidator;

    @Transactional
    public void update(UUID labelId, String newValue) {
        labelValueValidator.validate(newValue);

        Label label = labelQueryService.findByLabelIdValidated(labelId);
        label.setLabelValue(newValue);
        labelDao.save(label);
    }
}
