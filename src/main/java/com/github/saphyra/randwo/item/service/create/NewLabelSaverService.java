package com.github.saphyra.randwo.item.service.create;

import com.github.saphyra.randwo.label.service.create.CreateLabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class NewLabelSaverService {
    private final CreateLabelService createLabelService;

    List<UUID> saveLabels(List<String> newLabels) {
        return newLabels.stream()
            .map(createLabelService::createLabel)
            .collect(Collectors.toList());
    }
}
