package com.github.saphyra.randwo.item.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.label.service.create.CreateLabelService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewLabelSaverService {
    private final CreateLabelService createLabelService;

    public List<UUID> saveLabels(List<String> newLabels) {
        return newLabels.stream()
            .map(createLabelService::createLabel)
            .collect(Collectors.toList());
    }
}
