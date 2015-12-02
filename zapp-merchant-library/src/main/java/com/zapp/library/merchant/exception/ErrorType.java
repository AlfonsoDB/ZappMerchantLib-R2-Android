package com.zapp.library.merchant.exception;

/**
 * Enumeration of error types.
 *
 * @author msagi
 */
public enum ErrorType {
    /** Consumer custom error. */
    CONSUMER_CUSTOM_ERROR,
    /** No internet connection. */
    NETWORK_ERROR,
    /** APTRID error. */
    INVALID_APTRID,
    /** APTID error. */
    INVALID_APTID,
    /** Settlement retrieval id error. */
    INVALID_SETTLEMENT_RETRIEVAL_ID,
    /** Merchant id error. */
    INVALID_MERCHANT_ID,
    /** BRN error. */
    INVALID_BRN,
    /** Response error. */
    INVALID_SERVICE_DELEGATE_RESPONSE,
    /** Payment not confirmed error. */
    PAYMENT_NOT_CONFIRMED,
    /** Server response error. */
    INVALID_SERVER_RESPONSE,
    /** Generic validation error. */
    GENERIC_VALIDATION_ERROR,
    /** BRN expired error. */
    PAYMENT_BRN_EXPIRED,
    /** Generic internal error. */
    GENERIC_INTERNAL_ERROR
}
