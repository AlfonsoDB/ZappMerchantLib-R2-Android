package uk.co.zapp.samplezappmerchantapp.network.delegate;

import com.zapp.core.Address;
import com.zapp.core.DeliveryAddress;
import com.zapp.core.DeliveryAddressType;
import com.zapp.core.PaymentAuth;
import com.zapp.core.PaymentRequest;
import com.zapp.core.RetrievalMethod;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.core.TransactionStatus;
import com.zapp.core.User;
import com.zapp.core.ZappModelValidationException;
import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.OnResponseListener;
import com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.network.exception.GenericException;
import uk.co.zapp.samplezappmerchantapp.network.exception.NetworkException;
import uk.co.zapp.samplezappmerchantapp.network.exception.PaymentNotFinishedException;
import uk.co.zapp.samplezappmerchantapp.network.response.PaymentNotificationResponse;
import uk.co.zapp.samplezappmerchantapp.network.response.RTPResponse;
import uk.co.zapp.samplezappmerchantapp.network.response.SettlementStatusResponse;
import uk.co.zapp.samplezappmerchantapp.network.rest.MerchantRestClient;
import uk.co.zapp.samplezappmerchantapp.network.rest.RestClientProvider;
import uk.co.zapp.samplezappmerchantapp.util.Const;
import uk.co.zapp.samplezappmerchantapp.util.UserPrefs;

/**
 * Implementation of the merchant provider delegate.
 */
public class MerchantNetworkServiceDelegateImpl extends GenericDelegate implements IMerchantNetworkServiceDelegate {

    /**
     * The REST network client.
     */
    private final MerchantRestClient restClient;

    /**
     * Create new instance.
     *
     * @param context The context to use.
     */
    public MerchantNetworkServiceDelegateImpl(final Context context) {
        super(context);
        restClient = RestClientProvider.getRestClient(context);
    }

    @Override
    public void initiatePayment(final PaymentRequest paymentRequest, final OnResponseListener<Transaction> responseListener) {
        final Error networkError = checkNetwork();
        if (networkError == null) {
            try {
                final RTPResponse rtpResponse = restClient.createRTP(paymentRequest);

                final TransactionId transactionId = new TransactionId(rtpResponse.getAptId(), rtpResponse.getAptrId());

                final Transaction transaction = new Transaction(rtpResponse.getBrn(), transactionId, paymentRequest, /* paymentAuth */ null,
                        rtpResponse.getRetrievalExpiryInterval(), rtpResponse.getConfirmationExpiryInterval(), RetrievalMethod.INVOCATION,
                        paymentRequest.getMerchantCallbackUrl(), /* askForPayConnect */ null, /* isInitiatorAuthoriser */ null, rtpResponse.getSettlementRetrievalId(), rtpResponse.getNotificationSent());

                UserPrefs.writeTransactionObject(transaction, mContext);
                processAndDispatchOperationResult(null, transaction, responseListener);
            } catch (NetworkException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.NETWORK_ERROR);
                processAndDispatchOperationResult(error, null, responseListener);
            } catch (GenericException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.GENERIC_INTERNAL_ERROR);
                processAndDispatchOperationResult(error, null, responseListener);
            } catch (ZappModelValidationException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage());
                final Error error = new Error(ErrorType.GENERIC_INTERNAL_ERROR, null, e.getMessage());
                processAndDispatchOperationResult(error, null, responseListener);
            }
        } else {
            responseListener.onFailure(networkError);
        }
    }

    @Override
    public void notifyMerchantPayment(final Transaction transaction, final OnResponseListener<Transaction> responseListener) {
        final Error networkError = checkNetwork();
        if (networkError == null) {
            try {
                final String aptrId = transaction.getTransactionId().getAptrId();
                final PaymentNotificationResponse notificationResponse = restClient.notifyMerchantPayment(aptrId);

                final Transaction initialTransaction = UserPrefs.readTransactionObject(mContext);
                Transaction responseTransaction = initialTransaction == null ? transaction : initialTransaction;

                if (notificationResponse.getTransactionStatus() != TransactionStatus.IN_PROGRESS) {
                    responseTransaction = authorizePayment(responseTransaction, notificationResponse.getAddressDetails(), notificationResponse.getEmail(),
                            notificationResponse.getMobile(), notificationResponse.getTransactionStatus());
                }

                processAndDispatchOperationResult(null, responseTransaction, responseListener);
            } catch (NetworkException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.NETWORK_ERROR, mContext.getString(R.string.exception_network_title),
                        mContext.getString(R.string.exception_network_msg));
                processAndDispatchOperationResult(error, null, responseListener);
            } catch (PaymentNotFinishedException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.PAYMENT_NOT_CONFIRMED, null, e.getMessage());
                processAndDispatchOperationResult(error, null, responseListener);
            } catch (GenericException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.GENERIC_INTERNAL_ERROR);
                processAndDispatchOperationResult(error, null, responseListener);
            } catch (ZappModelValidationException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage());
                final Error error = new Error(ErrorType.GENERIC_INTERNAL_ERROR, null, e.getMessage());
                processAndDispatchOperationResult(error, null, responseListener);
            }
        } else {
            responseListener.onFailure(networkError);
        }
    }

    /**
     * Creates and populates the payment authentication object with payment request details and delivery address if the {@link TransactionStatus} is AUTHORIZED.
     *
     * @param transaction       The payment transaction.
     * @param deliveryAddress   The delivery address received from the CFI response
     * @param phone             The phone number.
     * @param transactionStatus The transaction status.
     */
    private Transaction authorizePayment(final Transaction transaction, final DeliveryAddress deliveryAddress, final String email, final String phone,
            final TransactionStatus transactionStatus)
            throws ZappModelValidationException {
        PaymentAuth paymentAuth;

        if (transactionStatus == TransactionStatus.AUTHORIZED) {
            final PaymentRequest paymentRequest = transaction.getPaymentRequest();

            if (deliveryAddress != null) {
                final User addressee = deliveryAddress.getAddressee();
                paymentAuth = new PaymentAuth(paymentRequest.getCheckoutType(), paymentRequest.getDeliveryType(), /*bankAccount*/ null,
                        addressee, paymentRequest.getAmount(), deliveryAddress, TransactionStatus.AUTHORIZED, /* askForPayConnect */ null);
            } else {
                User addressee = paymentRequest.getUser();
                if (addressee == null && !TextUtils.isEmpty(email)) {
                    addressee = new User(/*firstName*/ null, /*lastName*/ null, /*middle name*/ null, /*title*/ null, email, /*phone*/ null);
                }
                if (addressee != null) {
                    addressee.setPhone(phone);
                }
                DeliveryAddress delAddress = null;
                final Address address = paymentRequest.getAddress();
                if (address != null) {
                    delAddress = new DeliveryAddress(null, DeliveryAddressType.GENERAL, addressee, address.getLine1(), address.getLine2(),
                            address.getLine3(), address.getLine4(), address.getLine5(), address.getLine6(), address.getPostCode(), address.getCountryCode());
                }
                paymentAuth = new PaymentAuth(paymentRequest.getCheckoutType(), paymentRequest.getDeliveryType(), /*bankAccount*/ null,
                        addressee, paymentRequest.getAmount(), delAddress, TransactionStatus.AUTHORIZED, /* askForPayConnect */ null);
            }
        } else {
            paymentAuth = new PaymentAuth(/*checkoutType*/ null, /*deliveryType*/ null, /*bankAccount*/ null, /*user*/ null, /*finalAmount*/ null,
                    /*deliveryAddress*/ null, transactionStatus, /* askForPayConnect */ null);
        }
        transaction.setPaymentAuth(paymentAuth);
        return transaction;
    }

    @Override
    public void getSettlementStatus(final String merchantId, final String settlementRetrievalId, final OnResponseListener<SettlementStatus> responseListener) {
        final Error networkError = checkNetwork();
        if (networkError == null) {
            try {
                final SettlementStatusResponse settlementStatusResponse = restClient.getSettlementStatus(merchantId, settlementRetrievalId);
                final SettlementStatus settlementStatus = settlementStatusResponse.getSettlementStatus();
                processAndDispatchOperationResult(null, settlementStatus, responseListener);
            } catch (NetworkException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.NETWORK_ERROR);
                processAndDispatchOperationResult(error, null, responseListener);
            } catch (GenericException e) {
                Log.w(Const.DEBUG_TAG, e.getMessage(), e);
                final Error error = new Error(ErrorType.GENERIC_INTERNAL_ERROR);
                processAndDispatchOperationResult(error, null, responseListener);
            }
        } else {
            responseListener.onFailure(networkError);
        }
    }
}
