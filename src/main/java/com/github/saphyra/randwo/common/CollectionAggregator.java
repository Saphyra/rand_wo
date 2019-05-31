package com.github.saphyra.randwo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CollectionAggregator {

    public <T> List<T> aggregate(List<T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public <K, V> Map<K, V> aggregate(Map<K, V>... maps) {
        Map<K, V> result = new HashMap<>();
        for (Map<K, V> map : maps) {
            result.putAll(map);
        }
        return result;
    }
}
