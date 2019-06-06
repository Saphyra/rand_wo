package com.github.saphyra.randwo.storedobject.service;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoredObjectQueryService {
    private final StoredObjectDao storedObjectDao;

    public StoredObject findByObjectKeyValidated(String objectKey) {
        return storedObjectDao.findById(objectKey)
            .orElseThrow(() -> new NotFoundException(new ErrorMessage(ErrorCode.OBJECT_NOT_FOUND.getErrorCode()), "StoredObject not found with key " + objectKey));
    }
}
