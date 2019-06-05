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
public class IndexController {
    private static final String INDEX_MAPPING = "/";

    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(INDEX_MAPPING)
    ModelAndView index() {
        return modelAndViewFactory.create("index");
    }
}
