package com.github.saphyra.randwo.key.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeyQueryService {
    private final KeyDao keyDao;

    public Key findByKeyIdValidated(UUID keyId) {
        return keyDao.findById(keyId)
            .orElseThrow(() -> new NotFoundException(new ErrorMessage(ErrorCode.KEY_NOT_FOUND.getErrorCode()), "Key not found with keyId " + keyId));
    }
}
