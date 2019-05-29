package com.github.saphyra.randwo.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    EMPTY_LABEL_VALUE("empty-label-value"),
    LABEL_NOT_FOUND("label-not-found"),
    LABEL_VALUE_ALREADY_EXISTS("label-value-already-exists"),
    MAPPING_ALREADY_EXISTS("mapping-already-exists"),
    NO_ITEM_VALUES("no-item-values"),
    NO_LABELS("no-labels"),
    NULL_ITEM_VALUE("empty-item-value"),
    VALUE_IS_NULL("value-is-null");

    public static final String PARAMETER_KEY_NULL_VALUE = "parameter-null";

    private final String errorCode;
}
