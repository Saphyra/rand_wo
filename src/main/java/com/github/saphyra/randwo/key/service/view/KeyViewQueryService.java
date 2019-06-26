package com.github.saphyra.randwo.key.service.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.key.domain.KeyView;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KeyViewQueryService {
    private final KeyQueryService keyQueryService;
    private final KeyViewConverter keyViewConverter;

    public List<KeyView> getAll() {
        return keyQueryService.getAll().stream()
            .map(keyViewConverter::convert)
            .collect(Collectors.toList());
    }
}
