package com.github.saphyra.randwo.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PageController {
    private static final String INDEX_MAPPING = "/";

    @GetMapping(INDEX_MAPPING)
    String index(){
        return "index";
    }
}
