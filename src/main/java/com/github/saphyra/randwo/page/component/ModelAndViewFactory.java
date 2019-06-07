package com.github.saphyra.randwo.page.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.github.saphyra.randwo.page.domain.ModelAttribute;
import com.github.saphyra.randwo.page.domain.Page;

@Component
public class ModelAndViewFactory {
    public ModelAndView create(String pageName, ModelAttribute... modelAttributes) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("page", new Page(pageName));

        for (ModelAttribute attribute : modelAttributes) {
            modelMap.put(attribute.getKey(), attribute.getValue());
        }

        return new ModelAndView("template", modelMap);
    }
}
