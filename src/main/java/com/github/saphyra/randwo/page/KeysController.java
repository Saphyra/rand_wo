package com.github.saphyra.randwo.page;

import com.github.saphyra.randwo.page.component.ModelAndViewFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KeysController {
    static final String KEYS_MAPPING = "/keys";

    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(KEYS_MAPPING)
    ModelAndView keys() {
        log.info("Request arrived to {}", KEYS_MAPPING);
        return modelAndViewFactory.create("keys");
    }
}
