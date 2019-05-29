package com.github.saphyra.randwo.label.service.create;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.repository.LabelDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
class LabelValueValidator {
    private final LabelDao labelDao;

    void validate(String labelValue) {
        if (isBlank(labelValue)) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.EMPTY_LABEL_VALUE.getErrorCode()), "Label value is empty.");
        }

        if (labelDao.findByLabelValue(labelValue).isPresent()) {
            throw new BadRequestException(new ErrorMessage(ErrorCode.LABEL_VALUE_ALREADY_EXISTS.getErrorCode()), "Label already exists with value " + labelValue);
        }
    }
}
