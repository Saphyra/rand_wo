package com.github.saphyra.randwo.label.service.view;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelViewQueryService {
    private final LabelQueryService labelQueryService;
    private final LabelViewConverter labelViewConverter;

    public LabelView findByLabelId(UUID labelId) {
        return labelViewConverter.convert(labelQueryService.findByLabelIdValidated(labelId));
    }

    public List<LabelView> getAll() {
        return labelQueryService.getAll().stream()
            .map(labelViewConverter::convert)
            .collect(Collectors.toList());
    }
}
