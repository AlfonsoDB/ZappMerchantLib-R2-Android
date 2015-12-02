package uk.co.zapp.samplezappmerchantapp.network.response;

/**
 * Create request to pay response data object.
 *
 * Note: field names do not match coding standards. This is a trade off to be able to easily convert this data object to network request body using Gson.
 */
public class RTPResponse extends BaseServerResponse {

    /**
     * Basket reference number.
     */
    private String brn;

    /**
     * Transaction identifier.
     */
    private String aptId;

    /**
     * Alternative alternative payment transaction identifier.
     */
    private String aptrId;

    /**
     * The retrieval expiry interval value (in seconds).
     */
    private int retrievalExpiryInterval;

    /**
     * The payment confirmation expiry interval value (in seconds).
     */
    private int confirmationExpiryInterval;

    /**
     * The settlement retrieval id.
     */
    private String settlementRetrievalId;

    /**
     * Indicates if a payment notification was sent.
     */
    private boolean notificationSent;

    /**
     * Get the basket reference number.
     *
     * @return The {@link java.lang.String} basket reference number.
     */
    public String getBrn() {
        return brn;
    }

    /**
     * Get the transaction id.
     *
     * @return The {@link java.lang.String} transaction id.
     */
    public String getAptId() {
        return aptId;
    }

    /**
     * Get the alternative payment transaction identifier.
     *
     * @return The {@link java.lang.String} alternative payment transaction identifier.
     */
    public String getAptrId() {
        return aptrId;
    }

    /**
     * Get the retrieval expiry interval value (in seconds).
     *
     * @return The retrieval expiry interval value.
     */
    public int getRetrievalExpiryInterval() {
        return retrievalExpiryInterval;
    }

    /**
     * Get the payment confirmation expiry interval value (in seconds).
     *
     * @return The payment confirmation expiry interval value.
     */
    public int getConfirmationExpiryInterval() {
        return confirmationExpiryInterval;
    }

    /**
     * Get the settlement retrieval id.
     *
     * @return The settlement retrieval id.
     */
    public String getSettlementRetrievalId() {
        return settlementRetrievalId;
    }

    /**
     * Get the notification sent flag.
     *
     * @return The notification sent flag.
     */
    public boolean getNotificationSent() {
        return notificationSent;
    }
}
