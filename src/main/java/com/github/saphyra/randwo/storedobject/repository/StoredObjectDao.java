package com.github.saphyra.randwo.storedobject.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;

@Component
public class StoredObjectDao extends AbstractDao<StoredObjectEntity, StoredObject, String, StoredObjectRepository> {
    public StoredObjectDao(StoredObjectConverter converter, StoredObjectRepository repository) {
        super(converter, repository);
    }
}
