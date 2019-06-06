package com.github.saphyra.randwo.storedobject.service.save;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;

@RunWith(MockitoJUnitRunner.class)
public class StoredObjectFactoryTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @InjectMocks
    private StoredObjectFactory underTest;

    @Test
    public void create() {
        //GIVEN
        StoreObjectRequest request = StoreObjectRequest.builder()
            .key(KEY)
            .value(VALUE)
            .build();
        //WHEN
        StoredObject result = underTest.create(request);
        //THEN
        assertThat(result.getKey()).isEqualTo(KEY);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }
}