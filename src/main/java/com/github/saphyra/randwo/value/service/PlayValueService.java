package com.github.saphyra.randwo.value.service;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.common.Speaker;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.service.ItemValueMappingQueryService;
import com.github.saphyra.randwo.value.domain.PlayValueRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayValueService {
    private final ItemValueMappingQueryService itemValueMappingQueryService;
    private final Speaker speaker;

    public void playValue(PlayValueRequest request) {
        ItemValueMapping itemValueMapping = itemValueMappingQueryService.findByItemIdAndKeyIdValidated(request.getItemId(), request.getKeyId());
        speaker.speakValue(itemValueMapping.getValue());
    }
}
