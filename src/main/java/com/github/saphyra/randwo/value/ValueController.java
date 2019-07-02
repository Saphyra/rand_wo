package com.github.saphyra.randwo.value;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.value.domain.PlayValueRequest;
import com.github.saphyra.randwo.value.service.PlayValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
class ValueController {
    private static final String PLAY_VALUE_MAPPING = "/value/play";

    private final PlayValueService playValueService;

    @PostMapping(PLAY_VALUE_MAPPING)
    void playValue(@RequestBody PlayValueRequest request){
        log.info("playValue is called with request {}", request);
        playValueService.playValue(request);
    }
}
