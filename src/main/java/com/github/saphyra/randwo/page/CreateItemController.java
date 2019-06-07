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
public class CreateItemController {
    private static final String CREATE_ITEM_MAPPING = "items/create";

    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(CREATE_ITEM_MAPPING)
    public ModelAndView createItem(){
        log.info("Request arrived to {}", CREATE_ITEM_MAPPING);
        return modelAndViewFactory.create("create_item");
    }
}
