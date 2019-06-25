package com.github.saphyra.randwo.page.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.page.domain.ModelAttribute;
import com.github.saphyra.randwo.page.domain.Page;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelAndViewFactory {
    private final ObjectMapperDelegator objectMapperDelegator;

    public ModelAndView create(String pageName, ModelAttribute... modelAttributes) {
        return create(pageName, Collections.emptyList(), modelAttributes);
    }

    public ModelAndView create(String pageName, List<ModelAttribute> pageAttributes, ModelAttribute... modelAttributes) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("page", new Page(pageName));
        modelMap.put("pageData", objectMapperDelegator.writeValueAsString(convertToMap(pageAttributes)));

        for (ModelAttribute attribute : modelAttributes) {
            modelMap.put(attribute.getKey(), attribute.getValue());
        }

        return new ModelAndView("template", modelMap);
    }

    private Map<String, Object> convertToMap(List<ModelAttribute> modelAttributes) {
        return modelAttributes.stream()
            .collect(Collectors.toMap(ModelAttribute::getKey, ModelAttribute::getValue));
    }
}
