package com.zapp.core;

/**
 * Specifies whether it is an address that the consumer’s financial institution holds for the consumer or a different one.
 */
public enum DeliveryAddressType {

    /**
     * The default value for delivery address.
     */
    GENERAL,

    /**
     * Customer address stored by ZAPP.
     */
    ZAPP,

    /**
     * Address that the consumer’s financial institution holds.
     */
    CFI
}
