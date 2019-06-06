package com.github.saphyra.randwo.storedobject.service.save;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;

@Component
public class StoreObjectRequestValidator {
    public void validate(StoreObjectRequest request) {
        if (isNull(request.getKey())) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.NULL_OBJECT_KEY.getErrorCode()), "StoredObject key is null.");
        }

        if (isBlank(request.getKey())) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.EMPTY_OBJECT_KEY.getErrorCode()), "StoredObject key is empty.");
        }

        if (request.getKey().length() > 255) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.OBJECT_KEY_TOO_LONG.getErrorCode()), "StoredObject key is too long.");
        }
    }
}
