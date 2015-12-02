package com.zapp.library.merchant.ui.fragment;

import com.zapp.core.RTPType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionStatus;
import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.ProcessResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;
import com.zapp.library.merchant.ui.PaymentCountDownTimer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Starts a countdown timer which waits for the user to make the payment until the timeout expires. If user submits the Pay by Bank app code using another device,
 * it starts another timer that schedules a countdown until a time in the future when payment confirmation expires.
 * <br>
 * The {@link PaymentCountDownTimer} ticks every five seconds defined by the {@link #COUNTDOWN_INTERVAL} and makes a call to the server in order to check whether the
 * payment has been completed successfully.
 * <br>
 * In other words user should make a payment in a certain interval of time provided by the backend, one for Pay by Bank app code and another for payment confirmation.
 * <br>
 *
 * @author msagi
 */
public class PBBAPopupTimerFragment extends PBBAPopupFragment implements PaymentCountDownTimer.TimerListener {

    /**
     * Constant for one second in milliseconds.
     */
    private static final int ONE_SECOND_IN_MS = 1000;

    /**
     * Constant for countdown interval in milliseconds.
     */
    private static final int COUNTDOWN_INTERVAL = 5 * ONE_SECOND_IN_MS;

    private static final String TAG = PBBAPopupTimerFragment.class.getSimpleName();

    /**
     * Timer for basket reference number and payment confirmation countdowns.
     */
    private PaymentCountDownTimer mCountDownTimer;

    /**
     * The timer type.
     */
    private PaymentCountDownTimer.TimerType mTimerType;

    /**
     * The time (in millis) before the timer finishes.
     */
    private long mTimerMillisInFuture;

    /**
     * Indicates that the the notify call is in progress and do not call it again until it completes.
     */
    private boolean mIsPingInProgress;

    /**
     * Response listener for process response.
     */
    private final ProcessResponseListener<Transaction> mResponseListener = new ProcessResponseListener<Transaction>() {

        @Override
        public void onFailure(final Error error) {
            mIsPingInProgress = false;
            //this is a periodic check so it is not a problem if one check fails, this is why we do not call back
        }

        @Override
        public void onSuccess(final ResponseWrapper<Transaction> response) {
            mIsPingInProgress = false;
            final Transaction transaction = response.getResponse();
            final TransactionStatus transactionStatus = transaction.getTransactionStatus();
            if (transactionStatus == TransactionStatus.DECLINED) {
                if (mCallback != null) {
                    mCallback.onPaymentDeclined();
                }
            }

            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
        }
    };

    /**
     * Create new timer fragment instance.
     *
     * @param transaction The transaction to use for the timer.
     * @return The new timer fragment instance.
     */
    public static PBBAPopupTimerFragment newInstance(@NonNull Transaction transaction) {
        final PBBAPopupTimerFragment fragment = new PBBAPopupTimerFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable(KEY_TRANSACTION, transaction);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        final Bundle arguments = getArguments();
        mTransaction = (Transaction) arguments.getSerializable(KEY_TRANSACTION);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startTimer();
    }

    /**
     * Start the Zapp Code timer.
     */
    protected void startTimer() {
        if (mCountDownTimer == null) {
            //start the flow with the BRN countdown
            mTimerType = PaymentCountDownTimer.TimerType.TIMER_PAYMENT_RETRIEVAL;
            mTimerMillisInFuture = (long) mTransaction.getRetrievalExpiryInterval() * ONE_SECOND_IN_MS;
            mCountDownTimer = new PaymentCountDownTimer(mTimerType, this, mTimerMillisInFuture, COUNTDOWN_INTERVAL);
            mCountDownTimer.start();
        } else if (mTimerMillisInFuture == 0) {
            //repeat onFinish on rotation
            onFinish(mTimerType);
        }
    }

    @Override
    public void onTick(final PaymentCountDownTimer.TimerType timerType, final long millisUntilFinished) {
        //store remaining time to be able to restore it on fragment restart
        mTimerMillisInFuture = millisUntilFinished;
        Log.d(TAG, "onTick: type: " + timerType + ", remaining: " + millisUntilFinished);

        if (!mIsPingInProgress && isResumed()) {
            mIsPingInProgress = true;
            //poll for payment status
            mMerchantService.notifyMerchantPayment(mTransaction, mResponseListener);
        }
    }

    @Override
    public void onFinish(final PaymentCountDownTimer.TimerType timerType) {
        //clear remaining time if timer has finished
        mTimerMillisInFuture = 0L;
        final String merchantId = mTransaction.getPaymentRequest().getMerchant().getIdentifier();
        final String settlementRetrievalId = mTransaction.getSettlementRetrievalId();

        if (timerType == PaymentCountDownTimer.TimerType.TIMER_PAYMENT_RETRIEVAL) {
            if (mTransaction.getPaymentRequest().getRtpType() == RTPType.DEFERRED) {
                mMerchantService.notifyMerchantPayment(mTransaction, new DeferredPaymentNotifyListenerOnRetrievalTimeout());
            } else {
                mMerchantService.getSettlementStatus(merchantId, settlementRetrievalId, new SettlementStatusListenerOnRetrievalTimeout());
            }
        } else {
            if (mTransaction.getPaymentRequest().getRtpType() == RTPType.DEFERRED) {
                mMerchantService.notifyMerchantPayment(mTransaction, new DeferredPaymentNotifyListenerOnConfirmationTimeout());
            } else {
                mMerchantService.getSettlementStatus(merchantId, settlementRetrievalId, new SettlementStatusListenerOnConfirmationTimeout());
            }
        }
    }

    private class SettlementStatusListenerOnConfirmationTimeout extends StatusListenerOnConfirmationTimeout<SettlementStatus> {

        @Override
        public void onSuccess(final ResponseWrapper<SettlementStatus> response) {
            final SettlementStatus settlementStatus = response.getResponse();
            if (settlementStatus == SettlementStatus.AUTHORISED) {
                mMerchantService.notifyMerchantPayment(mTransaction, mResponseListener);
            } else if (settlementStatus == SettlementStatus.INCOMPLETE || settlementStatus == SettlementStatus.PAYMENT_ENQUIRY_FAILED) {
                if (mCallback != null) {
                    mCallback.onPaymentConfirmationExpired();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onPaymentDeclined();
                }
            }
        }
    }

    private class SettlementStatusListenerOnRetrievalTimeout extends StatusListenerOnRetrievalTimeout<SettlementStatus> {

        @Override
        public void onSuccess(final ResponseWrapper<SettlementStatus> response) {
            final SettlementStatus settlementStatus = response.getResponse();
            if (settlementStatus == SettlementStatus.IN_PROGRESS) {
                switchToPaymentConfirmationTimer();
            } else if (settlementStatus == SettlementStatus.INCOMPLETE || settlementStatus == SettlementStatus.PAYMENT_ENQUIRY_FAILED) {
                if (mCallback != null) {
                    mCallback.onPaymentRetrievalExpired();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onPaymentDeclined();
                }
            }
        }
    }

    private class DeferredPaymentNotifyListenerOnConfirmationTimeout extends StatusListenerOnConfirmationTimeout<Transaction> {

        @Override
        public void onSuccess(final ResponseWrapper<Transaction> response) {
            final Transaction transaction = response.getResponse();
            final TransactionStatus transactionStatus = transaction.getTransactionStatus();
            if (transactionStatus == TransactionStatus.INCOMPLETE || transactionStatus == TransactionStatus.PAYMENT_ENQUIRY_FAILED) {
                if (mCallback != null) {
                    mCallback.onPaymentConfirmationExpired();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onPaymentDeclined();
                }
            }
        }
    }

    private class DeferredPaymentNotifyListenerOnRetrievalTimeout extends StatusListenerOnRetrievalTimeout<Transaction> {

        @Override
        public void onSuccess(final ResponseWrapper<Transaction> response) {
            final Transaction transaction = response.getResponse();
            final TransactionStatus transactionStatus = transaction.getTransactionStatus();
            if (transactionStatus == TransactionStatus.IN_PROGRESS) {
                switchToPaymentConfirmationTimer();
            } else if (transactionStatus == TransactionStatus.INCOMPLETE || transactionStatus == TransactionStatus.PAYMENT_ENQUIRY_FAILED) {
                if (mCallback != null) {
                    mCallback.onPaymentRetrievalExpired();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onPaymentDeclined();
                }
            }
        }
    }

    private abstract class StatusListenerOnRetrievalTimeout<T> extends ProcessResponseListener<T> {

        /**
         * Starts the payment confirmation timer.
         */
        protected void switchToPaymentConfirmationTimer() {
            //continue the flow with the Payment Confirmation countdown
            mTimerType = PaymentCountDownTimer.TimerType.TIMER_PAYMENT_CONFIRMATION;
            mTimerMillisInFuture = (long) mTransaction.getConfirmationExpiryInterval() * ONE_SECOND_IN_MS;
            mCountDownTimer = new PaymentCountDownTimer(mTimerType, PBBAPopupTimerFragment.this, mTimerMillisInFuture, COUNTDOWN_INTERVAL);
            mCountDownTimer.start();
        }

        @Override
        public void onFailure(final Error error) {
            //TODO the distributor gateway should return a new error code related to 'payment retrieval expired' case
            //in case of failure to poll transaction status at the payment retrieval expiry, move on to payment confirmation window and continue polling
            switchToPaymentConfirmationTimer();
        }
    }

    private abstract class StatusListenerOnConfirmationTimeout<T> extends ProcessResponseListener<T> {

        @Override
        public void onFailure(final Error error) {
            if (mTimerMillisInFuture == 0) {
                final Error timeOutError = new Error(ErrorType.PAYMENT_BRN_EXPIRED, null, error.getErrorMessage(getActivity()));
                mCallback.onError(timeOutError);
            } else {
                mCallback.onError(error);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }
}
