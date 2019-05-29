package com.github.saphyra.randwo.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ObjectMapperDelegator {
    private final ObjectMapper objectMapper;

    public <T extends Map<?, ?>> T readMapValue(String source, Class<T> type) {
        try {
            return objectMapper.readValue(source, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(String source, TypeReference<T> type) {
        try {
            return objectMapper.readValue(source, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(String source, Class<T> clazz) {
        try {
            return objectMapper.readValue(source, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> readArrayValue(String source, Class<T[]> clazz) {
        try {
            return new ArrayList<>(Arrays.asList(objectMapper.readValue(source, clazz)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
