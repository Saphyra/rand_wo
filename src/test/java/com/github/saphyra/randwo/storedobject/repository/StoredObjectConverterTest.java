package com.github.saphyra.randwo.storedobject.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.storedobject.domain.StoredObject;

@RunWith(MockitoJUnitRunner.class)
public class StoredObjectConverterTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @InjectMocks
    private StoredObjectConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        StoredObjectEntity entity = StoredObjectEntity.builder()
            .key(KEY)
            .value(VALUE)
            .build();
        //WHEN
        StoredObject result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getKey()).isEqualTo(KEY);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        StoredObject entity = StoredObject.builder()
            .key(KEY)
            .value(VALUE)
            .build();
        //WHEN
        StoredObjectEntity result = underTest.processDomainConversion(entity);
        //THEN
        assertThat(result.getKey()).isEqualTo(KEY);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }
}