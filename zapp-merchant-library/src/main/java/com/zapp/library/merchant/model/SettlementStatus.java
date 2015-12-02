package com.zapp.library.merchant.model;

/**
 * The transactions settlement states.
 */
public enum SettlementStatus {
    /**
     * The payment transaction was successful and authorised by the FI.
     */
    AUTHORISED,
    /**
     * The transaction was declined.
     */
    DECLINED,
    /**
     * The incomplete state.
     */
    INCOMPLETE,
    /**
     * The payment transaction is not Authorised or Declined, it is still being processed.
     */
    IN_PROGRESS,
    /**
     * Transaction error if an payment status enquiry fails for a technical reason.
     */
    PAYMENT_ENQUIRY_FAILED,
    /**
     * The settlement transaction has never been authorised
     */
    NEVER_AUTHORISED,
    /**
     * The settlement transaction has been late authorised.
     */
    LATE_AUTHORISED,
    /**
     * The settlement transaction has never been confirmed.
     */
    NEVER_CONFIRMED
}
