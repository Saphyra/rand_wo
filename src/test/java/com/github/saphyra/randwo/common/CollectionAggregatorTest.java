package com.github.saphyra.randwo.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CollectionAggregatorTest {
    private static final String KEY_1 = "key_1";
    private static final String KEY_2 = "key_2";
    private static final String VALUE_1 = "value_1";
    private static final String VALUE_2 = "value_2";

    @InjectMocks
    private CollectionAggregator underTest;

    @Test
    public void aggregate_list() {
        //GIVEN
        List<String> list1 = Arrays.asList(VALUE_1);
        List<String> list2 = Arrays.asList(VALUE_2);
        //WHEN
        List<String> result = underTest.aggregate(list1, list2);
        //THEN
        assertThat(result).containsOnly(VALUE_1, VALUE_2);
    }

    @Test
    public void aggregate_map() {
        //GIVEN
        Map<String, String> map1 = new HashMap<>();
        map1.put(KEY_1, VALUE_1);
        Map<String, String> map2 = new HashMap<>();
        map2.put(KEY_2, VALUE_2);
        //WHEN
        Map<String, String> result = underTest.aggregate(map1, map2);
        //THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(KEY_1)).isEqualTo(VALUE_1);
        assertThat(result.get(KEY_2)).isEqualTo(VALUE_2);
    }
}