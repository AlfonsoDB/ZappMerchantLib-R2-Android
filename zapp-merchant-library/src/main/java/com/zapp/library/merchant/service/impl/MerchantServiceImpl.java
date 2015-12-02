package com.zapp.library.merchant.service.impl;

import com.zapp.core.MessageType;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionStatus;
import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.OnResponseListener;
import com.zapp.library.merchant.model.callback.ProcessResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;
import com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate;
import com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate;
import com.zapp.library.merchant.service.provider.IMerchantService;
import com.zapp.library.merchant.service.provider.NetworkServiceDelegateSupplier;
import com.zapp.library.merchant.service.provider.UIDelegateSuplier;
import com.zapp.library.merchant.util.AppUtils;
import com.zapp.library.merchant.util.SharedPrefs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * A Service class that provides the necessary methods for making a payment.
 * It also ensures the validity of the data that is passed and retrieved from the backend.
 * All methods are executed in the {@link AbstractZappMerchantService.ZappServiceExecutorThread} which means
 * that they are executed in a separate thread. The communication with the core is made through delegates which user must extend and implement.
 *
 * @author Vasile Chelban on 6/27/2014.
 */
public final class MerchantServiceImpl extends AbstractZappMerchantService implements IMerchantService, NetworkServiceDelegateSupplier, UIDelegateSuplier {

    /**
     * Tag for logging.
     */
    private static final String TAG = MerchantServiceImpl.class.getSimpleName();

    /**
     * The merchant service implementation singleton.
     */
    private static MerchantServiceImpl sInstance;

    /**
     * THe merchant network service delegate.
     */
    private IMerchantNetworkServiceDelegate mServiceDelegate;

    /**
     * The UI delegate.
     */
    private IZappMerchantUIDelegate mUIDelegate;

    /**
     * Create new instance.
     *
     * @param serviceDelegate The merchant network service delegate.
     * @param uiDelegate      The UI delegate.
     */
    private MerchantServiceImpl(final IMerchantNetworkServiceDelegate serviceDelegate, final IZappMerchantUIDelegate uiDelegate) {
        super();
        mServiceDelegate = serviceDelegate;
        mUIDelegate = uiDelegate;
    }

    /**
     * Get the merchant service instance.
     *
     * @return The {@link IMerchantService} merchant service instance.
     */
    static IMerchantService getInstance() {
        return sInstance;
    }

    /**
     * Get the current merchant service instance.
     *
     * @return The {@link MerchantServiceImpl} current merchant service instance.
     */
    static MerchantServiceImpl getCurrentInstance() {
        return sInstance;
    }

    @Override
    public IMerchantNetworkServiceDelegate getNetworkServiceDelegate() {
        return getServiceDelegate();
    }

    /**
     * Create new merchant service instance.
     *
     * @param serviceDelegate   The merchant network service delegate to use with this instance.
     * @param paymentUIDelegate The UI delegate instance to use with this instance.
     */
    public static IMerchantService newInstance(final IMerchantNetworkServiceDelegate serviceDelegate, final IZappMerchantUIDelegate paymentUIDelegate) {
        if (sInstance == null) {
            sInstance = new MerchantServiceImpl(serviceDelegate, paymentUIDelegate);
        } else if (serviceDelegate != sInstance.getServiceDelegate() && paymentUIDelegate != sInstance.getUIDelegate()) {
            //checking if delegates have changed in the meantime
            sInstance.setServiceDelegate(serviceDelegate);
            sInstance.setUIDelegate(paymentUIDelegate);
        }
        return sInstance;
    }

    /**
     * Set the merchant network service delegate.
     *
     * @param serviceDelegate The merchant network service delegate to use.
     */
    private void setServiceDelegate(final IMerchantNetworkServiceDelegate serviceDelegate) {
        this.mServiceDelegate = serviceDelegate;
    }

    /**
     * Set the UI delegate.
     *
     * @param UIDelegate The UI delegate to use.
     */
    private void setUIDelegate(final IZappMerchantUIDelegate UIDelegate) {
        this.mUIDelegate = UIDelegate;
    }

    /**
     * Get the merchant network service delegate.
     *
     * @return The {@link IMerchantNetworkServiceDelegate} merchant network service delegate.
     */
    public IMerchantNetworkServiceDelegate getServiceDelegate() {
        return mServiceDelegate;
    }

    /**
     * Get the UI delegate.
     *
     * @return The {@link IZappMerchantUIDelegate} UI delegate.
     */
    public IZappMerchantUIDelegate getUIDelegate() {
        return mUIDelegate;
    }

    @Override
    public void initiatePayment(@NonNull final Context applicationContext, @NonNull final PaymentRequest paymentRequest,
            @Nullable final ProcessResponseListener<Transaction> responseListener) {
        addRunnable(new Runnable() {
            @Override
            public void run() {
                if (responseListener != null) {
                    executeOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            responseListener.onStart();
                        }
                    });
                }

                final boolean isTablet = AppUtils.isCurrentDeviceTablet(applicationContext);
                final boolean hasBankApp = AppUtils.isAnyZappBankAppAvailable(applicationContext);
                final boolean isSMBPayment = paymentRequest.getPaymentType() == PaymentType.SMB;
                if (isSMBPayment) {
                    if (paymentRequest.getMessageType() != MessageType.BRN) {
                        Log.w(TAG,
                                "Payment request message type is automatically corrected to BRN: message type must be BRN if payment type is set to SMB or running on tablet");
                        paymentRequest.setMessageType(MessageType.BRN);
                    }
                } else {
                    final SharedPrefs sharedPrefs = new SharedPrefs(applicationContext);
                    final boolean openBankAppAutomatically = hasBankApp && sharedPrefs.isOpenBankingAppButtonClicked();
                    paymentRequest.setMessageType(!isTablet && openBankAppAutomatically ? MessageType.MOBILE : MessageType.GENERAL);
                }

                //disable auto bank opening when bank app is not installed (or has been uninstalled)
                final SharedPrefs sharedPrefs = new SharedPrefs(applicationContext);
                if (!hasBankApp) {
                    sharedPrefs.setOpenBankingAppButtonClicked(false);
                }

                mServiceDelegate.initiatePayment(paymentRequest, new OnResponseListener<Transaction>() {

                    private void fail(final Error error) {
                        executeOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (responseListener != null) {
                                    responseListener.onFailure(error);
                                }
                                mUIDelegate.showPopupDialog(null, paymentRequest, error);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final Error error) {
                        fail(error);
                    }

                    @Override
                    public void onSuccess(final ResponseWrapper<Transaction> response) {

                        //if gateway request was successful but returned no response then we still consider it as a failure
                        if (response == null) {
                            fail(new Error(ErrorType.INVALID_SERVER_RESPONSE));
                            return;
                        }

                        final Transaction responseTransaction = response.getResponse();

                        //if gateway response does not contain any transaction then we still consider it as a failure
                        if (responseTransaction == null) {
                            fail(new Error(ErrorType.INVALID_SERVER_RESPONSE));
                            return;
                        }

                        if (isSMBPayment) {
                            if (TextUtils.isEmpty(responseTransaction.getBrn())) {
                                //SMB payment is based on BRN. If response transaction does not have BRN code then we consider this as a failure
                                fail(new Error(ErrorType.INVALID_BRN));
                            } else {
                                executeOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (responseListener != null) {
                                            responseListener.onSuccess(response);
                                        }
                                        mUIDelegate.showSMBScreen(responseTransaction);
                                    }
                                });
                            }
                        } else {
                            executeOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (responseListener != null) {
                                        responseListener.onSuccess(response);
                                    }
                                    mUIDelegate.showPopupDialog(responseTransaction, /* paymentRequest */ null, /* error */ null);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void notifyMerchantPayment(final Transaction transaction, final ProcessResponseListener<Transaction> responseListener) {
        assertListenerNotNull(responseListener);
        addRunnable(new Runnable() {
            @Override
            public void run() {
                if (transaction.getTransactionId() != null && transaction.getTransactionId().getAptrId() == null) {
                    responseListener.onFailure(new Error(ErrorType.INVALID_APTRID));
                } else {
                    executeOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            responseListener.onStart();
                        }
                    });
                    mServiceDelegate.notifyMerchantPayment(transaction, new OnResponseListener<Transaction>() {

                        @Override
                        public void onFailure(final Error error) {
                            executeOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    final ErrorType errorType = error.getType();
                                    if (errorType != ErrorType.PAYMENT_NOT_CONFIRMED && errorType != ErrorType.NETWORK_ERROR) {
                                        mUIDelegate.showPaymentConfirmationScreen(null, error);
                                    }
                                    responseListener.onFailure(error);
                                }
                            });
                        }

                        @Override
                        public void onSuccess(final ResponseWrapper<Transaction> response) {
                            if (response == null) {
                                final Error error = new Error(ErrorType.INVALID_SERVICE_DELEGATE_RESPONSE);
                                executeOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mUIDelegate.showPaymentConfirmationScreen(null, error);
                                    }
                                });
                                responseListener.onFailure(error);//todo: add specific error messge for this kind of scenarios
                            } else {
                                final Transaction responseTransaction = response.getResponse();
                                executeOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TransactionStatus transactionStatus = responseTransaction.getTransactionStatus();
                                        if (transactionStatus == TransactionStatus.AUTHORIZED || transactionStatus == TransactionStatus.DECLINED) {
                                            mUIDelegate.showPaymentConfirmationScreen(responseTransaction, null);
                                        }
                                        responseListener.onSuccess(new ResponseWrapper<Transaction>(responseTransaction));
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getSettlementStatus(final String merchantId, final String settlementRetrievalId, final OnResponseListener<SettlementStatus> responseListener) {
        assertListenerNotNull(responseListener);
        addRunnable(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(settlementRetrievalId)) {
                                executeOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        responseListener.onFailure(new Error(ErrorType.INVALID_SETTLEMENT_RETRIEVAL_ID));
                                    }
                                });
                            } else if (TextUtils.isEmpty(merchantId)) {
                                executeOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        responseListener.onFailure(new Error(ErrorType.INVALID_MERCHANT_ID));
                                    }
                                });
                            } else {
                                mServiceDelegate.getSettlementStatus(merchantId, settlementRetrievalId, new OnResponseListener<SettlementStatus>() {

                                    @Override
                                    public void onFailure(final Error error) {
                                        executeOnUIThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                responseListener.onFailure(error);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onSuccess(final ResponseWrapper<SettlementStatus> response) {
                                        executeOnUIThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                responseListener.onSuccess(response);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }
        );
    }
}
