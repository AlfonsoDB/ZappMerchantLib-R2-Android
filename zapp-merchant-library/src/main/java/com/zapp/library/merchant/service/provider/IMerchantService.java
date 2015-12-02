package com.zapp.library.merchant.service.provider;

import com.zapp.core.PaymentRequest;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionStatus;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.OnResponseListener;
import com.zapp.library.merchant.model.callback.ProcessResponseListener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} that exposes payment-related functionality for merchants.
 * <br>
 * Central point to access all payment-related functionality, like initiating or checking the status of a payment. <br>
 * <br>
 * To get an instance of this {@link com.zapp.library.merchant.service.provider.IMerchantService} one of the following methods should be used:
 * <ul>
 * <li>{@link com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory#createMerchantService(com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate,
 * com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate)} (
 * </li>
 * <li>{@link com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory#getMerchantService()},
 * which returns an existing instance or <code>null</code>, if
 * {@link com.zapp.library.merchant.service.provider.IMerchantService IMerchantService} wasn't created yet.</li>
 * </ul>
 * <br>
 * <br>
 * This {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} makes use of
 * {@link com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate IMerchantNetworkServiceDelegate}
 * and {@link com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate IZappMerchantUIDelegate} to invoke network-specific
 * calls or to show UI elements (i.e.: show BRN page or payment confirmation).
 * <br>
 * All calls to this {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} are
 * non-blocking and thread-safe (can be invoked from UI thread also), using provided
 * {@link OnResponseListener OnResponseListener} to notify when an operation has been completed or failed.
 * <br>
 * <b style='color:red'>Please note:</b>
 * <ul>
 * <li>
 * Implementation of this {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} is Singleton.
 * </li>
 * </ul>
 * <br>
 * <br>
 *
 * @author Vasile Chelban on 6/27/2014.
 */
public interface IMerchantService {

    /**
     * Initiates a payment using the provided payment {@link com.zapp.core.Transaction} details
     * which describes all the details about a payment, like {@link com.zapp.core.PaymentType PaymentType}
     * or {@link com.zapp.core.BillDetails BillDetails}.
     * <br>
     * This method is thread-safe (i.e.: can be called without blocking the caller). Can be invoked from any {@link Thread},
     * {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} having its own executor thread.
     * <br>
     * Caller will be notified through {@link OnResponseListener responseListener}
     * in case of success or failure.
     * <br>
     * <b style='color:red'>Note:</b> All callbacks are called on UI thread.
     *
     * @param context          application context
     * @param paymentRequest   All details required to initiate a merchant payment journey, like
     *                         {@link com.zapp.core.PaymentType PaymentType}
     *                         or {@link com.zapp.core.BillDetails BillDetails}.
     * @param responseListener {@link ProcessResponseListener ProcessResponseListener}
     *                         providing callbacks for success and error scenarios while initiating
     *                         a payment using the provided payment {@link com.zapp.core.Transaction} details or any
     *                         network-related {@link com.zapp.library.merchant.exception.Error error}.
     *                         Also, notifies the listener when network call it's about to be started,
     *                         so that any progress could be displayed. If null, no callback will be provided.
     */
    void initiatePayment(@NonNull Context applicationContext, @NonNull PaymentRequest paymentRequest, @Nullable ProcessResponseListener<Transaction> responseListener);

    /**
     * Checks a payment's status using the provided payment {@link com.zapp.core.Transaction} details
     * which should contain, at least, a non-null {@link com.zapp.core.TransactionId transactionId}
     * containing an <code>aptrID</code>, which uniquely identifies a pending payment {@link com.zapp.core.Transaction}.
     * <br>
     * This method is thread-safe (i.e.: can be called without blocking the caller). Can be invoked from any {@link Thread},
     * {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} having its own executor thread.
     * <br>
     * Caller will be notified through {@link OnResponseListener responseListener}
     * in case of success or failure.
     * <br>
     * <b style='color:red'>Note:</b> All callbacks are called on UI thread.
     *
     * @param transaction      Payment transaction details containing a non-null {@link com.zapp.core.TransactionId transactionId}
     *                         containing an <code>aptrID</code> which uniquely identifies a
     *                         pending payment {@link com.zapp.core.Transaction}.
     *                         <br>
     * @param responseListener {@link ProcessResponseListener ProcessResponseListener}
     *                         providing callbacks for success and error scenarios while checking
     *                         the payment' status using the provided using the provided <code>aptrID</code>
     *                         or any network-related {@link com.zapp.library.merchant.exception.Error error}.
     *                         <br>
     *                         If an error with errorCode
     *                         {@link com.zapp.library.merchant.exception.ErrorType#PAYMENT_NOT_CONFIRMED ErrorType#PAYMENT_NOT_CONFIRMED}
     *                         is received on failure, then merchant library automatically send
     *                         another call for checking the payment status using the current method.
     *                         <br>
     *                         Also, notifies the listener when network call it's about to be started,
     *                         so that any progress could be displayed.
     */
    void notifyMerchantPayment(Transaction transaction, ProcessResponseListener<Transaction> responseListener);

    /**
     * Retrieves the settlement status using the merchantId and settlementRetrievalId.
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
