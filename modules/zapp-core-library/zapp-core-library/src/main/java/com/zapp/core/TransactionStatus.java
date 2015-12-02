package com.zapp.core;

/**
 * The payment status for a specific point in time.
 */
public enum TransactionStatus {
    /**
     * The payment transaction is not Authorised or Declined, it is still being processed.
     */
    IN_PROGRESS,

    /**
     * The payment transaction was successful and authroised by the FI.
     */
    AUTHORIZED,

    /**
     * The payment transaction was declined.
     */
    DECLINED,

    /**
     * Incomplete. The payment transaction is in logical state of ‘Retrieval Timeout’, ‘Late Retrieval’, ‘Confirmation Timeout’, ‘Late Authorised’, ‘Late Consumer
     * Declined’ or ‘Late FI Declined’.
     */
    INCOMPLETE,

    /**
     * Transaction error if an payment status enquiry fails for a technical reason.
     */
    PAYMENT_ENQUIRY_FAILED,

    /**
     * When the deferred order is authorised but awaiting settlement.
     */
    AWAITING_SETTLEMENT,
}
