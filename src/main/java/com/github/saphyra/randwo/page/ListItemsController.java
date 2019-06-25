package com.github.saphyra.randwo.page;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.page.component.ModelAndViewFactory;
import com.github.saphyra.randwo.page.domain.ModelAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ListItemsController {
    private static final String LIST_ITEMS_MAPPING = "/items";

    private final KeyDao keyDao;
    private final LabelDao labelDao;
    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(LIST_ITEMS_MAPPING)
    ModelAndView listItems(
        @RequestParam(value = "label", required = false) UUID labelId,
        @RequestParam(value = "key", required = false) UUID keyId
    ) {
        log.info("Request arrived to {}", LIST_ITEMS_MAPPING);
        return modelAndViewFactory.create(
            "list_items",
            new ModelAttribute("label", Optional.ofNullable(labelId).flatMap(labelDao::findById).map(Label::getLabelValue).orElse("")),
            new ModelAttribute("key", Optional.ofNullable(keyId).flatMap(keyDao::findById).map(Key::getKeyValue).orElse(""))
        );
    }
}
