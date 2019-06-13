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
public class RandomItemController {
    private static final String RANDOM_ITEM_MAPPING = "items/random";

    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(RANDOM_ITEM_MAPPING)
    public ModelAndView randomItem() {
        log.info("Request arrived to {}", RANDOM_ITEM_MAPPING);
        return modelAndViewFactory.create("random_item");
    }
}
