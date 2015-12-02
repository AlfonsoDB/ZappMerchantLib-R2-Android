package com.zapp.core;

/**
 * For both journeys (e-commerce, m-commerce), consumers have the option of using the ‘QuickPay with Zapp’ feature.
 */
public enum CheckoutType {

    /**
     * Standard payment.
     */
    NORMAL,

    /**
     * For quick payment consumer does not have to log into the merchant website or enter their address details.
     */
    QUICK
}
