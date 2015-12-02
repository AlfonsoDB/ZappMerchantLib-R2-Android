package com.zapp.core;

/**
 * The payment details retrieval method. It can be retrieved using the {@link com.zapp.core.TransactionId} sent via push notification or by another merchant app
 * installed on the same Android device as the bank app. Another method is the manually introducing of a special BRN code by the user from another device where the bank
 * app is installed.
 */
public enum RetrievalMethod {

    /**
     * Submit the BRN code.
     */
    BRN,

    /**
     * The Merchant app sends the {@link com.zapp.core.TransactionId} to the bank app installed on the same device via an intent.
     */
    INVOCATION,

    /**
     * The bank app receives the {@link com.zapp.core.TransactionId} via push notification.
     */
    PUSH_NOTIFICATION
}
