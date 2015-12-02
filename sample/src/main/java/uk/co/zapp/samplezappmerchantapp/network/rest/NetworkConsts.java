package uk.co.zapp.samplezappmerchantapp.network.rest;

/**
 * Network constants.
 */
public interface NetworkConsts {

    /**
     * URL for create request to pay.
     */
    String CREATE_RTP_URL = "/transaction";

    /**
     * URL to poll to check if payment is still pending.
     */
    String POLL_FOR_PAYMENT_NOTIFICATION_URL = "/transaction/{aptrid}";

    /**
     * URL for enquire settlement status.
     */
    String SETTLEMENT_STATUS_URL = "/transaction/{merchantId}/{settlementRetrievalId}";
}
