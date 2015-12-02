package com.zapp.library.merchant.ui;

import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.library.merchant.R;
import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory;
import com.zapp.library.merchant.service.provider.IMerchantService;
import com.zapp.library.merchant.ui.fragment.PBBAAnimatedPopupFragment;
import com.zapp.library.merchant.ui.fragment.PBBAPopupECommFragment;
import com.zapp.library.merchant.ui.fragment.PBBAPopupErrorFragment;
import com.zapp.library.merchant.ui.fragment.PBBAPopupFragment;
import com.zapp.library.merchant.ui.fragment.PBBAPopupMCommFragment;
import com.zapp.library.merchant.ui.fragment.PBBAPopupTimerFragment;
import com.zapp.library.merchant.util.AppUtils;
import com.zapp.library.merchant.util.Const;
import com.zapp.library.merchant.util.SharedPrefs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import java.lang.ref.WeakReference;

/**
 * An activity which displays the Pay by Bank app popups.
 *
 * @author msagi
 */
public final class PBBAPopupActivity extends FragmentActivity implements IPBBAPopupCallback {

    /**
     * Popup transition state code for 'current popup is closing' state.
     */
    private static final int POPUP_TRANSITION_STATE_CURRENT_POPUP_IS_CLOSING = 0;

    /**
     * Popup transition state code for 'next popup is opening' state.
     */
    private static final int POPUP_TRANSITION_STATE_NEXT_POPUP_IS_OPENING = 1;

    /**
     * Custom handler to handle popup transitions.
     */
    private static class PopupHandler extends Handler {

        private final WeakReference<PBBAPopupActivity> mActivityWeakReference;

        private PopupHandler(@NonNull final PBBAPopupActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(final Message msg) {
            final PBBAPopupActivity activity = mActivityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            final int state = msg.what;
            final PBBAAnimatedPopupFragment nextPopupFragment = (PBBAAnimatedPopupFragment) msg.obj;

            switch (state) {
                case POPUP_TRANSITION_STATE_CURRENT_POPUP_IS_CLOSING:
                    final FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    final PBBAAnimatedPopupFragment currentPopupFragment = (PBBAAnimatedPopupFragment) fragmentManager.findFragmentByTag(TAG_POPUP);
                    if (currentPopupFragment != null) {
                        currentPopupFragment.animateOut(new AbstractAnimationListener() {
                            @Override
                            public void onAnimationEnd(final Animation animation) {
                                Message.obtain(PopupHandler.this, POPUP_TRANSITION_STATE_NEXT_POPUP_IS_OPENING, nextPopupFragment).sendToTarget();
                            }
                        });
                    } else {
                        Message.obtain(PopupHandler.this, POPUP_TRANSITION_STATE_NEXT_POPUP_IS_OPENING, nextPopupFragment).sendToTarget();
                    }
                    break;
                case POPUP_TRANSITION_STATE_NEXT_POPUP_IS_OPENING:
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.pbba_popup_dialog_frame, nextPopupFragment, TAG_POPUP)
                            .commitAllowingStateLoss(); //allow state loss in case a callback would happen when the activity is in the background already (e.g. mcomm journey)
                    break;
                default:
                    Log.w(TAG, "Unknown popup transition state: code: " + state);
                    break;
            }
        }
    }

    /**
     * Tag for logging.
     */
    private static final String TAG = PBBAPopupActivity.class.getSimpleName();

    /**
     * Constant for payment request object key.
     */
    public static final String KEY_PAYMENT_REQUEST_OBJECT = "payment_request_object_key";

    /**
     * Constant for payment object key.
     */
    public static final String KEY_PAYMENT_OBJECT = "payment_object_key";

    /**
     * Constant for payment error
     */
    public static final String KEY_PAYMENT_ERROR = "payment_error";

    /**
     * Constant for key to track if code has been displayed.
     */
    private static final String KEY_HAS_CODE_BEEN_DISPLAYED = "hasCodeBeenDisplayed";

    /**
     * Constant for fragment tag of popup fragments.
     */
    private static final String TAG_POPUP = "tag.popup";

    /**
     * Custom handler for popup transition animations.
     */
    private final PopupHandler mPopupTransitionHandler = new PopupHandler(this);

    /**
     * The payment request for this popup screen.
     */
    private PaymentRequest mPaymentRequest;

    /**
     * The transaction for this popup screen.
     */
    private Transaction mTransaction;

    /**
     * The error for this popup screen.
     */
    private Error mError;

    /**
     * Flag to keep track if Pay by Bank app code has been displayed.
     */
    private boolean mHasCodeBeenDisplayed = false;

    /**
     * The timer fragment instance. Used to retain timer state on rotation but does not render UI.
     */
    private PBBAPopupTimerFragment mTimerFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pbba_popup_layout);

        if (!AppUtils.isCurrentDeviceTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            final Configuration config = getResources().getConfiguration();
            Log.d(this.getClass().getSimpleName(), String.format("Smallest width is [%s] dpi", config.smallestScreenWidthDp));
        } else {
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            final int widthDpi = (int) (displayMetrics.widthPixels / displayMetrics.density);
            Log.d(this.getClass().getSimpleName(), String.format("Width is [%s] dpi", widthDpi));
        }

        final Bundle state = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        loadState(state);

        if (savedInstanceState == null) {
            selectAndShowPopup();
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        loadState(intent.getExtras());
        selectAndShowPopup();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_PAYMENT_OBJECT, mTransaction);
        savedInstanceState.putSerializable(KEY_PAYMENT_REQUEST_OBJECT, mPaymentRequest);
        savedInstanceState.putSerializable(KEY_PAYMENT_ERROR, mError);
        savedInstanceState.putSerializable(KEY_HAS_CODE_BEEN_DISPLAYED, mHasCodeBeenDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Load the state of this popup screen from the given data bundle.
     *
     * @param state The bundle containing state data for this popup screen.
     */
    private void loadState(final Bundle state) {
        mTransaction = (Transaction) state.getSerializable(KEY_PAYMENT_OBJECT);
        mPaymentRequest = (PaymentRequest) state.getSerializable(KEY_PAYMENT_REQUEST_OBJECT);
        mError = (com.zapp.library.merchant.exception.Error) state.getSerializable(KEY_PAYMENT_ERROR);
        mHasCodeBeenDisplayed = state.getBoolean(KEY_HAS_CODE_BEEN_DISPLAYED, false);
    }

    /**
     * Get the proper journey type for the current transaction.
     *
     * @return The journey type.
     * @see PBBAPopupFragment.JourneyType
     */
    private PBBAPopupFragment.JourneyType getJourneyType() {
        if (mHasCodeBeenDisplayed) {
            //the flow is locked to ecomm journey if the code has been displayed before
            return PBBAPopupFragment.JourneyType.ECOMM;
        }

        final boolean isBankAppInstalled = AppUtils.isAnyZappBankAppAvailable(this);

        final PaymentType paymentType;
        if (mPaymentRequest != null) {
            //if this is an error before the transaction has been created, we have payment request here
            paymentType = mPaymentRequest.getPaymentType();
        } else {
            //if this is an error after the transaction has been created (e.g. timeout), we have transaction here
            paymentType = mTransaction.getPaymentRequest().getPaymentType();
        }
        final boolean isSMBPayment = paymentType == PaymentType.SMB;

        if (isBankAppInstalled && !isSMBPayment) {
            final boolean isFirstTimeJourney = !(new SharedPrefs(getApplicationContext()).isOpenBankingAppButtonClicked());
            if (isFirstTimeJourney) {
                return PBBAPopupFragment.JourneyType.FIRST_TIME;
            } else {
                return PBBAPopupFragment.JourneyType.MCOMM;
            }
        } else {
            return PBBAPopupFragment.JourneyType.ECOMM;
        }
    }

    /**
     * Select and show popup.
     */
    private void selectAndShowPopup() {

        if (mError != null) {
            showErrorPopup();
            return;
        }

        final PBBAPopupFragment.JourneyType journeyType = getJourneyType();
        switch (journeyType) {
            case ECOMM:
                showECommPopup();
                break;
            case FIRST_TIME:
                showMCommPopup();
                break;
            case MCOMM:
                //skip the mcomm popup and open the bank app automatically
                handleBankAppAutoOpen();
                break;
        }
    }

    /**
     * Opens the bank application to process a new transaction with mcomm journey.
     */
    private void handleBankAppAutoOpen() {
        final TransactionId transactionId = mTransaction.getTransactionId();
        final Intent intent = AppUtils.createBankingAppIntent(transactionId.getAptId(), transactionId.getAptrId());
        startActivity(intent);
        finish();
    }

    private void startTimer() {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        mTimerFragment = (PBBAPopupTimerFragment) fragmentManager.findFragmentByTag(PBBAPopupTimerFragment.class.getSimpleName());

        if (mTimerFragment == null) {

            mTimerFragment = PBBAPopupTimerFragment.newInstance(mTransaction);
            fragmentManager
                    .beginTransaction()
                    .add(mTimerFragment, PBBAPopupTimerFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
            Log.d(TAG, "Timer fragment added.");
        } else {
            Log.d(TAG, "Timer has already been set.");
        }
    }

    private void cancelTimer() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        mTimerFragment = (PBBAPopupTimerFragment) fragmentManager.findFragmentByTag(PBBAPopupTimerFragment.class.getSimpleName());

        if (mTimerFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(mTimerFragment)
                    .commitAllowingStateLoss();
            Log.d(TAG, "Timer fragment removed.");
        } else {
            Log.d(TAG, "Cannot remove timer fragment: it is not added.");
        }
    }

    /**
     * Show a popup on the screen with an animated transition.
     *
     * @param fragment The popup fragment to display.
     */
    private void showPopup(final PBBAPopupFragment fragment) {
        Message.obtain(mPopupTransitionHandler, POPUP_TRANSITION_STATE_CURRENT_POPUP_IS_CLOSING, fragment).sendToTarget();
    }

    private void showErrorPopup() {
        final PBBAPopupErrorFragment fragment = PBBAPopupErrorFragment.newInstance(getJourneyType(), mError);
        cancelTimer();
        showPopup(fragment);
    }

    private void showMCommPopup() {
        final PBBAPopupMCommFragment fragment = PBBAPopupMCommFragment.newInstance(mTransaction);
        showPopup(fragment);
        startTimer();
    }

    private void showECommPopup() {
        mHasCodeBeenDisplayed = true;
        final PBBAPopupECommFragment fragment = PBBAPopupECommFragment.newInstance(mTransaction);
        showPopup(fragment);
        startTimer();
    }

    //
    // IPBBAPopupCallback interface
    //

    @Override
    public void onPaymentConfirmationExpired() {
        mError = new Error(ErrorType.PAYMENT_BRN_EXPIRED);
        showErrorPopup();
    }

    @Override
    public void onPaymentRetrievalExpired() {
        mError = new Error(ErrorType.PAYMENT_BRN_EXPIRED);
        showErrorPopup();
    }

    @Override
    public void onPaymentDeclined() {
        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void onError(final com.zapp.library.merchant.exception.Error error) {
        mError = error;
        showErrorPopup();
    }

    @Override
    public void onDisplayCode() {
        showECommPopup();
    }

    public void onPayByBankApp() {
        final IMerchantService merchantService = ZappMerchantServiceFactory.getMerchantService();
        final PaymentRequest paymentRequest = mTransaction == null ? mPaymentRequest : mTransaction.getPaymentRequest();
        merchantService.initiatePayment(getApplicationContext(), paymentRequest, /* responseListener */ null);
    }

    @Override
    public void onNoBankAppAvailable() {
        showECommPopup();
    }

    public void onDisplayWhatIsPayByBankApp(final View view) {
        try {
            final Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Const.WHAT_IS_PAY_BY_BANK_APP_LINK));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            //TODO display a toast saying: install a web browser; needs to be discussed with Liam/Daniela
        }
    }
}
