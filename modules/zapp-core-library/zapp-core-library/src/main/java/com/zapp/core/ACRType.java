package com.zapp.core;

/**
 * Authentication check required type.
 * @author msagi
 */
public enum ACRType {
    /**
     * Neither check availability of fund nor secure the Deferred Txn Amount.
     */
    NONE,

    /**
     * Check availability of funds.
     */
    FUNDS_CHECK,

    /**
     * Secure the Deferred Txn Amount.
     */
    FUNDS_GUARANTEE,

    /**
     * Do fund check at later GHS message.
     */
    LATER
}
