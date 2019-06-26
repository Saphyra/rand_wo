package com.github.saphyra.randwo.page;

import static com.github.saphyra.randwo.page.LabelsController.LABELS_MAPPING;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.service.LabelQueryService;
import com.github.saphyra.randwo.page.component.ModelAndViewFactory;
import com.github.saphyra.randwo.page.domain.ModelAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EditLabelController {
    private static final String EDIT_LABEL_MAPPING = "labels/edit/{labelId}";

    private final LabelQueryService labelQueryService;
    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(EDIT_LABEL_MAPPING)
    public ModelAndView createItem(@PathVariable("labelId") UUID labelId) {
        log.info("Request arrived to {}", EDIT_LABEL_MAPPING);

        try {
            return modelAndViewFactory.create(
                "edit_label",
                Arrays.asList(
                    new ModelAttribute("labelId", labelId)
                ),
                new ModelAttribute("labelValue", labelQueryService.findByLabelIdValidated(labelId).getLabelValue())
            );
        } catch (NotFoundException e) {
            if (e.getErrorMessage().getErrorCode().equals(ErrorCode.LABEL_NOT_FOUND.getErrorCode())) {
                log.warn("Label not found. Redirecting...");
                return new ModelAndView("forward:" + LABELS_MAPPING);
            }

            throw e;
        }
    }
}
