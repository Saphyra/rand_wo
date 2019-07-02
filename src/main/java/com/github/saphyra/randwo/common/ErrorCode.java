package com.github.saphyra.randwo.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ARGUMENT_MISMATCH("argument-mismatch"),
    EMPTY_ITEM_IDS("empty-item-ids"),
    EMPTY_KEY_VALUE("empty-key-value"),
    EMPTY_LABEL_VALUE("empty-label-value"),
    EMPTY_OBJECT_KEY("empty-object-key"),
    GENERAL_ERROR("general-error"),
    ITEM_HAS_ONLY_ONE_KEY("item-has-only-one-key"),
    ITEM_HAS_ONLY_ONE_LABEL("item-has-only-one-label"),
    ITEM_LABEL_MAPPING_ALREADY_EXISTS("item-label-mapping-already-exists"),
    ITEM_NOT_FOUND("item-not-found"),
    ITEM_VALUE_MAPPING_ALREADY_EXISTS("item-value-mapping-already-exists"),
    ITEM_VALUE_MAPPING_NOT_FOUND("item-value-mapping-not-found"),
    KEY_NOT_FOUND("key-not-found"),
    KEY_VALUE_ALREADY_EXISTS("key-value-already-exists"),
    LABEL_NOT_FOUND("label-not-found"),
    LABEL_VALUE_ALREADY_EXISTS("label-value-already-exists"),
    NO_ITEM_VALUES("no-item-values"),
    NO_LABELS("no-labels"),
    NULL_EXISTING_KEY_VALUE_IDS("null-existing-key-value-ids"),
    NULL_EXISTING_LABEL_IDS("null-existing-label-ids"),
    NULL_IN_EXISTING_KEY_VALUES("null-in-existing-key-values"),
    NULL_IN_EXISTING_LABEL_IDS("null-in-existing-label-ids"),
    NULL_IN_LABEL_IDS("null-in-label-ids"),
    NULL_IN_KEY_IDS("null-in-key-ids"),
    NULL_IN_NEW_KEY_VALUES("null-in-new-key-values"),
    NULL_IN_NEW_LABELS("null-in-new-labels"),
    NULL_ITEM_DELETE_METHOD("null-item-delete-method"),
    NULL_ITEM_ID("null-item-id"),
    NULL_ITEM_VALUE("empty-item-value"),
    NULL_KEY_IDS("null-key-ids"),
    NULL_LABEL_IDS("null-label-ids"),
    NULL_NEW_KEY_VALUES("null-new-key-values"),
    NULL_NEW_LABELS("null-new-values"),
    NULL_OBJECT_KEY("null-object-key"),
    OBJECT_KEY_TOO_LONG("object-key-too-long"),
    OBJECT_NOT_FOUND("object-not-found"),
    RANDOM_ITEM_CANNOT_BE_SELECTED("random-item-cannot-be-selected"),
    REQUEST_BODY_MISSING("request-body-missing");

    public static final String PARAMETER_KEY_NULL_VALUE = "parameter-null";

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
