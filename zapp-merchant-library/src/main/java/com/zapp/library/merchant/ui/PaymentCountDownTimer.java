package com.zapp.library.merchant.ui;

import android.os.CountDownTimer;

/**
 * An implementation of {@link android.os.CountDownTimer} used for Payment retrieval and Payment confirmation expiration tracking.
 *
 * @author gdragan on 7/17/2014.
 */
public class PaymentCountDownTimer extends CountDownTimer {

    /**
     * List of timer types.
     */
    public enum TimerType {
        /**
         * Timer type for payment retrieval. The payment transaction must be retrieved in the bank app before this timer goes onFinish.
         */
        TIMER_PAYMENT_RETRIEVAL,
        /**
         * Timer type for payment confirmation. The payment transaction must be confirmed in the bank app before this timer goes onFinish.
         */
        TIMER_PAYMENT_CONFIRMATION
    }

    /**
     * The timer type (default: payment retrieval timer).
     */
    private TimerType mTimerType = TimerType.TIMER_PAYMENT_RETRIEVAL;

    /**
     * The timer listener.
     */
    private TimerListener mListener;

    /**
     * Create new instance.
     *
     * @param timerType         The timer type.
     * @param listener          The timer listener.
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public PaymentCountDownTimer(final TimerType timerType, final TimerListener listener, final long millisInFuture, final long countDownInterval) {
        this(millisInFuture, countDownInterval);
        this.mTimerType = timerType;
        this.mListener = listener;
    }

    /**
     * Create new instance.
     *
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    private PaymentCountDownTimer(final long millisInFuture, final long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(final long millisUntilFinished) {
        if (mListener != null) {
            mListener.onTick(mTimerType, millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (mListener != null) {
            mListener.onFinish(mTimerType);
        }
    }

    /**
     * Timer listener interface.
     * @see com.zapp.library.merchant.ui.fragment.PBBAPopupTimerFragment
     */
    public interface TimerListener {

        /**
         * 'Tick' event callback. Called when the timer ticks (every time when approximately 'count down interval' amount of time has elapsed).
         *
         * @param timerType           The timer type.
         * @param millisUntilFinished The millis until finished.
         */
        void onTick(TimerType timerType, long millisUntilFinished);

        /**
         * 'Finish' event callback. Called when the timer has reached zero time remaining.
         *
         * @param timerType The timer type.
         */
        void onFinish(TimerType timerType);
    }
}
