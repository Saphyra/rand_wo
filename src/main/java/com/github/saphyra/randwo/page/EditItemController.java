package com.github.saphyra.randwo.page;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import com.github.saphyra.randwo.page.component.ModelAndViewFactory;
import com.github.saphyra.randwo.page.domain.ModelAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EditItemController {
    private static final String EDIT_ITEM_MAPPING = "items/edit/{itemId}";

    private final ItemLabelMappingDao itemLabelMappingDao;
    private final ItemValueMappingDao itemValueMappingDao;
    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(EDIT_ITEM_MAPPING)
    public ModelAndView createItem(@PathVariable("itemId") UUID itemId) {
        log.info("Request arrived to {}", EDIT_ITEM_MAPPING);

        return modelAndViewFactory.create(
            "create_item",
            Arrays.asList(
                new ModelAttribute("labels", getLabels(itemId)),
                new ModelAttribute("keys", getKeys(itemId)),
                new ModelAttribute("itemId", itemId)
            )
        );
    }

    private List<UUID> getLabels(UUID itemId) {
        return itemLabelMappingDao.getByItemId(itemId).stream()
            .map(ItemLabelMapping::getLabelId)
            .collect(Collectors.toList());
    }

    private Map<UUID, String> getKeys(UUID itemId) {
        return itemValueMappingDao.getByItemId(itemId).stream()
            .collect(Collectors.toMap(ItemValueMapping::getKeyId, ItemValueMapping::getValue));
    }
}
