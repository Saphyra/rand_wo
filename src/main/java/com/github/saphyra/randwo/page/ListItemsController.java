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
public class ListItemsController {
    private static final String LIST_ITEMS_MAPPING = "/items";

    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(LIST_ITEMS_MAPPING)
    ModelAndView listItems() {
        log.info("Request arrived to {}", LIST_ITEMS_MAPPING);
        return modelAndViewFactory.create("list_items");
    }
}
