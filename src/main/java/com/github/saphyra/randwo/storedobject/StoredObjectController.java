package com.github.saphyra.randwo.storedobject;

import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.service.save.SaveObjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StoredObjectController {
    public static final String GET_OBJECT_MAPPING = "/stored-object/{key}";
    public static final String SAVE_OBJECT_MAPPING = "/stored-object";

    private final SaveObjectService saveObjectService;
    private final StoredObjectDao storedObjectDao;

    @GetMapping(GET_OBJECT_MAPPING)
    ResponseEntity<StoredObject> getObject(@PathVariable("key") String objectKey) {
        log.info("Querying StoredObject with key {}", objectKey);
        return storedObjectDao.findById(objectKey).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(SAVE_OBJECT_MAPPING)
    void saveObject(@RequestBody StoreObjectRequest request) {
        log.info("Saving object {}", request);
        saveObjectService.save(request);
    }
}
