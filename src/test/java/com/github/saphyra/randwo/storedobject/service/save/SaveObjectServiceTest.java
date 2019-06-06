package com.github.saphyra.randwo.storedobject.service.save;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;

@RunWith(MockitoJUnitRunner.class)
public class SaveObjectServiceTest {
    @Mock
    private StoredObjectDao storedObjectDao;

    @Mock
    private StoredObjectFactory storedObjectFactory;

    @Mock
    private StoreObjectRequestValidator storeObjectRequestValidator;

    @InjectMocks
    private SaveObjectService underTest;

    @Mock
    private StoreObjectRequest storeObjectRequest;

    @Mock
    private StoredObject storedObject;

    @Test
    public void save() {
        //GIVEN
        given(storedObjectFactory.create(storeObjectRequest)).willReturn(storedObject);
        //WHEN
        underTest.save(storeObjectRequest);
        //THEN
        verify(storeObjectRequestValidator).validate(storeObjectRequest);
        verify(storedObjectDao).save(storedObject);
    }
}