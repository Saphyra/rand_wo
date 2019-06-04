package com.github.saphyra.randwo.label;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.label.service.delete.DeleteLabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LabelController {
    public static final String DELETE_LABELS_MAPPING = "/label";

    private final DeleteLabelService deleteLabelService;

    @DeleteMapping(DELETE_LABELS_MAPPING)
    public void deleteLabels(@RequestBody List<UUID> labelIds) {
        log.info("Deleting labels with id {}", labelIds);
        deleteLabelService.deleteLabels(labelIds);
    }
}
