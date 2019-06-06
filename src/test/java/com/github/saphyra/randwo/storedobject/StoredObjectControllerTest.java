package com.github.saphyra.randwo.storedobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.service.StoredObjectQueryService;
import com.github.saphyra.randwo.storedobject.service.save.SaveObjectService;

@RunWith(MockitoJUnitRunner.class)
public class StoredObjectControllerTest {
    private static final String OBJECT_KEY = "object_key";

    @Mock
    private SaveObjectService saveObjectService;

    @Mock
    private StoredObjectQueryService storedObjectQueryService;

    @InjectMocks
    private StoredObjectController underTest;

    @Mock
    private StoredObject storedObject;

    @Mock
    private StoreObjectRequest storeObjectRequest;

    @Test
    public void getObject() {
        //GIVEN
        given(storedObjectQueryService.findByObjectKeyValidated(OBJECT_KEY)).willReturn(storedObject);
        //WHEN
        StoredObject result = underTest.getObject(OBJECT_KEY);
        //THEN
        assertThat(result).isEqualTo(storedObject);
    }

    @Test
    public void saveObject() {
        //WHEN
        underTest.saveObject(storeObjectRequest);
        //THEN
        verify(saveObjectService).save(storeObjectRequest);
    }
}