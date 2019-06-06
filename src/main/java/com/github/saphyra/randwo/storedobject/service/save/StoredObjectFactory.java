package com.github.saphyra.randwo.storedobject.service.save;

import org.springframework.stereotype.Component;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;

@Component
public class StoredObjectFactory {
    public StoredObject create(StoreObjectRequest request) {
        return StoredObject.builder()
            .key(request.getKey())
            .value(request.getValue())
            .build();
    }
}
