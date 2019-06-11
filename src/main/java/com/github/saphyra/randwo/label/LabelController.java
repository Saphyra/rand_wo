package com.github.saphyra.randwo.label;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.label.service.delete.DeleteLabelService;
import com.github.saphyra.randwo.label.service.update.UpdateLabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LabelController {
    public static final String DELETE_LABELS_MAPPING = "/label";
    public static final String GET_LABELS = "/label";
    public static final String UPDATE_LABEL_MAPPING = "/label/{labelId}";

    private final DeleteLabelService deleteLabelService;
    private final LabelQueryService labelQueryService;
    private final UpdateLabelService updateLabelService;

    @DeleteMapping(DELETE_LABELS_MAPPING)
    public void deleteLabels(@RequestBody List<UUID> labelIds) {
        log.info("Deleting labels with id {}", labelIds);
        deleteLabelService.deleteLabels(labelIds);
    }

    @GetMapping(GET_LABELS)
    public List<Label> getLabels(){
        log.info("Querying all labels");
        return labelQueryService.getAll();
    }

    @PostMapping(UPDATE_LABEL_MAPPING)
    public void updateLabel(
        @PathVariable("labelId") UUID labelId,
        @RequestBody String newValue
    ){
        log.info("Updating label with id {} to value {}", labelId, newValue);
        updateLabelService.update(labelId, newValue);
    }
}
