package com.github.saphyra.randwo.label.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelQueryService {
    private final LabelDao labelDao;

    public Label findByLabelIdValidated(UUID labelId) {
        return labelDao.findById(labelId)
            .orElseThrow(() -> new NotFoundException(new ErrorMessage(ErrorCode.LABEL_NOT_FOUND.getErrorCode()), "Label not found with labelId " + labelId));
    }

    public List<Label> getAll() {
        return labelDao.getAll();
    }
}
