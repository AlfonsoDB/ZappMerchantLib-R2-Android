package com.zapp.core;

/**
 * The payment type.
 */
public enum PaymentType {
    //The order of these constants are important from the gateway point of you (it uses PaymentType .ordinal() values)

    /**
     * Bill payment, typically used for Utility bills such as Gas, Electricity and Water.
     */
    BILL_PAY,

    /**
     * Payment of purchased products or services – Most normal payments use this.
     */
    INSTANT_PAYMENT,

    /**
     * Face to face one off payments using a Merchant Mobile device to generate and submit an RTP before displaying a Zapp Code to the consumer for entry into the mobile
     * banking app on their mobile device (Historically Small/Micro Business).
     */
    SMB
}
