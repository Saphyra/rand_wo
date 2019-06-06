package com.github.saphyra.randwo.storedobject.service.save;

import org.springframework.stereotype.Service;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveObjectService {
    private final StoredObjectDao storedObjectDao;
    private final StoredObjectFactory storedObjectFactory;
    private final StoreObjectRequestValidator storeObjectRequestValidator;

    public void save(StoreObjectRequest request) {
        storeObjectRequestValidator.validate(request);

        storedObjectDao.save(storedObjectFactory.create(request));
    }
}
