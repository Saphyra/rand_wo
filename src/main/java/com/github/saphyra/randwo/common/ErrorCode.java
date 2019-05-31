package com.github.saphyra.randwo.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    EMPTY_KEY_VALUE("empty-key-value"),
    EMPTY_LABEL_VALUE("empty-label-value"),
    ITEM_NOT_FOUND("item-not-found"),
    KEY_NOT_FOUND("key-not-found"),
    KEY_VALUE_ALREADY_EXISTS("key-value-already-exists"),
    LABEL_NOT_FOUND("label-not-found"),
    LABEL_VALUE_ALREADY_EXISTS("label-value-already-exists"),
    ITEM_LABEL_MAPPING_ALREADY_EXISTS("item-label-mapping-already-exists"),
    ITEM_VALUE_MAPPING_ALREADY_EXISTS("item-value-mapping-already-exists"),
    NO_ITEM_VALUES("no-item-values"),
    NO_LABELS("no-labels"),
    NULL_ITEM_VALUE("empty-item-value"),
    VALUE_IS_NULL("value-is-null");

    public static final String PARAMETER_KEY_NULL_VALUE = "parameter-null";

    private final String errorCode;
}
