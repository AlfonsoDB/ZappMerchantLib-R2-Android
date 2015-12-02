package com.zapp.library.merchant.service.delegate;

import com.zapp.core.PaymentRequest;
import com.zapp.core.Transaction;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.OnResponseListener;

/**
 * {@link com.zapp.library.merchant.service.delegate.IZappMerchantServiceDelegate Merchant Service delegate}
 * for payment-related methods which should be specific for each merchant (Zapp Merchant library consumer). <br>
 * This {@link com.zapp.library.merchant.service.delegate.IZappMerchantServiceDelegate service delegate}
 * is used for initiating or checking the status of a payment using distributor's infrastructure.
 * <br>
 * This {@link com.zapp.library.merchant.service.delegate.IZappMerchantServiceDelegate service delegate}
 * is called from a Zapp internal executor {@link java.lang.Thread}. <br>
 * Implementers can execute blocking calls (i.e.: network/DB connections) directly or can use their
 * own asynchronous execution and notify through callbacks about the progress.
 * <br>
 * In case you want blocking calls to be executed in parallel, it's recommended to have your
 * own Thread/AsyncTask/Service.
 * <br>
 */
public interface IMerchantNetworkServiceDelegate extends IZappMerchantServiceDelegate {

    /**
     * Initiates a payment using the provided {@link com.zapp.core.Transaction} details
     * which describes all the details about a payment, like {@link com.zapp.core.PaymentType PaymentType}
     * or {@link com.zapp.core.BillDetails BillDetails}.
     * <br>
     * Implementers are completely responsible for handling network or DB calls. <br>
     * Implementation can execute blocking calls (network/DB calls) in current Thread or have an
     * asynchronous execution in place.
     * <br>
     * This method is invoked only after a successful validation of payment details and a new RTP(request-to-pay)
     * is to be created.
     * <br>
     * Caller should be notified through {@link OnResponseListener responseListener}
     * in case of success or failure.
     *
     * @param paymentRequest     All details required to initiate a merchant payment journey, like
     *                           {@link com.zapp.core.PaymentType PaymentType}
     *                           or {@link com.zapp.core.BillDetails BillDetails}.
     *                           You can use {@link com.zapp.library.merchant.model.PaymentRequestBuilder} to
     *                           build a {@link PaymentRequest} object you need.
     * @param onResponseListener {@link OnResponseListener OnResponseListener}
     *                           providing callbacks for success and error scenarios while initiating
     *                           an RTP using the provided {@link com.zapp.core.Transaction} details or any
     *                           network-related {@link com.zapp.library.merchant.exception.Error error}.
     */
    void initiatePayment(PaymentRequest paymentRequest, OnResponseListener<Transaction> onResponseListener);

    /**
     * Checks a payment's status using the provided {@link com.zapp.core.Transaction} details
     * which should contain, at least, a non-null {@link com.zapp.core.TransactionId transactionId}
     * containing an <code>aptrId</code> which uniquely identifies a {@link com.zapp.core.Transaction}.
     * <br>
     * Implementers are completely responsible for handling network or DB calls. <br>
     * Implementation can execute blocking calls (network/DB calls) in current Thread or have an
     * asynchronous execution in place.
     * <br>
     * Caller should be notified through {@link OnResponseListener responseListener}
     * in case of success or failure.
     *
     * @param transaction        Payment details containing a non-null {@link com.zapp.core.TransactionId transactionId}
     *                           containing an <code>aptrID</code> which uniquely identifies a
     *                           pending {@link com.zapp.core.Transaction}.
     *                           <br>
     * @param onResponseListener {@link OnResponseListener OnResponseListener}
     *                           providing callbacks for success and error scenarios while checking
     *                           payment' status using the provided <code>aptrID</code> or any
     *                           network-related {@link com.zapp.library.merchant.exception.Error error}.
     *                           If an error with errorCode
     *                           {@link com.zapp.library.merchant.exception.ErrorType#PAYMENT_NOT_CONFIRMED ErrorType#PAYMENT_NOT_CONFIRMED}
     *                           is received on failure, then merchant library automatically send
     *                           another call for checking the payment status using the current method.
     */
    void notifyMerchantPayment(Transaction transaction, OnResponseListener<Transaction> onResponseListener);

    /**
     * Retrieves the current settlement status in Zapp using the merchantId and settlementRetrievalId.
     * <br>
     * <br>
     * Caller should be notified through {@link OnResponseListener responseListener} in case of success or failure.
     *
     * @param merchantId            The merchant's id.
     * @param settlementRetrievalId The settlement retrieval id.
     * @param responseListener      {@link OnResponseListener OnResponseListener} providing callbacks for success and error scenarios.
     */
    void getSettlementStatus(String merchantId, String settlementRetrievalId, OnResponseListener<SettlementStatus> responseListener);

}
