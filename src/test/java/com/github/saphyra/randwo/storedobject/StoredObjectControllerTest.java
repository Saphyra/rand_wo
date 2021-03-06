package com.github.saphyra.randwo.storedobject;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;
import com.github.saphyra.randwo.storedobject.service.save.SaveObjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StoredObjectControllerTest {
    private static final String OBJECT_KEY = "object_key";

    @Mock
    private SaveObjectService saveObjectService;

    @Mock
    private StoredObjectDao storedObjectDao;

    @InjectMocks
    private StoredObjectController underTest;

    @Mock
    private StoredObject storedObject;

    @Mock
    private StoreObjectRequest storeObjectRequest;

    @Test
    public void getObject_found() {
        //GIVEN
        given(storedObjectDao.findById(OBJECT_KEY)).willReturn(Optional.of(storedObject));
        //WHEN
        ResponseEntity<StoredObject> result = underTest.getObject(OBJECT_KEY);
        //THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(storedObject);
    }

    @Test
    public void getObject_notFound() {
        //GIVEN
        given(storedObjectDao.findById(OBJECT_KEY)).willReturn(Optional.empty());
        //WHEN
        ResponseEntity<StoredObject> result = underTest.getObject(OBJECT_KEY);
        //THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveObject() {
        //WHEN
        underTest.saveObject(storeObjectRequest);
        //THEN
        verify(saveObjectService).save(storeObjectRequest);
    }
}