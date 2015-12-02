package com.zapp.core;

/**
 * (Transaction) Message type.
 *
 * @author msagi
 */
public enum MessageType {
    /**
     * The transaction is started from a mobile phone which has bank app installed (so it will not use BRN code).
     */
    MOBILE,
    /**
     * The transaction is an E-COM transaction requiring a BRN (or Zapp) code or an M-COM transaction where the bank application is not installed on the device.
     */
    BRN,
    /**
     * The mixture of the two above, the transaction is started on a device which has bank app installed but we want to display BRN code too.
     */
    GENERAL,
}

