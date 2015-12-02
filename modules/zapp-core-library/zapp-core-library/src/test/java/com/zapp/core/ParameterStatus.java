package com.zapp.core;

/**
 * The list of available parameter statuses.
 */
public enum ParameterStatus {
    /**
     * The parameter is valid.
     */
    valid,
    /**
     * The parameter is invalid.
     */
    invalid,
    /**
     * The parameter is not set (== null).
     */
    is_null,
}
