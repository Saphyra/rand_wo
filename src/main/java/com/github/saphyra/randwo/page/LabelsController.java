package com.github.saphyra.randwo.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.saphyra.randwo.page.component.ModelAndViewFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LabelsController {
    static final String LABELS_MAPPING = "/labels";

    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(LABELS_MAPPING)
    ModelAndView labels() {
        log.info("Request arrived to {}", LABELS_MAPPING);
        return modelAndViewFactory.create("labels");
    }
}
