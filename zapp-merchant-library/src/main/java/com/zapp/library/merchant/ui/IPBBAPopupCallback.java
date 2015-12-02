package com.zapp.library.merchant.ui;

/**
 * Callback interface to propagate events from popup fragments to the popup controller.
 *
 * @author msagi
 */
public interface IPBBAPopupCallback {

    /**
     * Event callback for 'payment retrieval expired' event.
     */
    void onPaymentRetrievalExpired();

    /**
     * Event callback for 'payment confirmation expired' event.
     */
    void onPaymentConfirmationExpired();

    /**
     * Event callback for 'payment declined' event.
     */
    void onPaymentDeclined();

    /**
     * Event callback for 'error' event.
     *
     * @param error The error instance.
     */
    void onError(com.zapp.library.merchant.exception.Error error);

    /**
     * Event callback for 'display code' event.
     */
    void onDisplayCode();

    /**
     * Event callback for 'Pay by Bank app' (initiate payment) event.
     */
    void onPayByBankApp();

    /**
     * Event callback for 'no banking app available' event.
     */
    void onNoBankAppAvailable();
}
