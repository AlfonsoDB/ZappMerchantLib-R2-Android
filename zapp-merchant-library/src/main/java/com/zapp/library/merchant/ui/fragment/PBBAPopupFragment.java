package com.zapp.library.merchant.ui.fragment;

import com.zapp.core.PaymentRequest;
import com.zapp.core.Transaction;
import com.zapp.library.merchant.R;
import com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory;
import com.zapp.library.merchant.service.provider.IMerchantService;
import com.zapp.library.merchant.ui.IPBBAPopupCallback;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

/**
 * The base Zapp Popup Fragment which holds the common data used by its children.
 */
public abstract class PBBAPopupFragment extends Fragment {

    /**
     * The list of journeys supported by the popups.
     */
    public enum JourneyType {
        /** First time usage journey type. */
        FIRST_TIME,
        /** E-commerce journey type. */
        ECOMM,
        /** M-commerce journey type. */
        MCOMM
    }

    /**
     * Fragment argument key for 'journey type'.
     */
    public static final String KEY_JOURNEY_TYPE = "journeyType";

    /**
     * Fragment argument key for 'error'.
     */
    public static final String KEY_ERROR = "error";

    /**
     * Fragment argument key for 'transaction'.
     */
    public static final String KEY_TRANSACTION = "transaction";

    /**
     * Fragment argument key for 'payment request'.
     */
    public static final String KEY_PAYMENT_REQUEST = "paymentRequest";

    /**
     * The payment request details.
     */
    protected PaymentRequest mPaymentRequest;

    /**
     * The payment details.
     */
    protected Transaction mTransaction;


    /**
     * The merchant service.
     */
    protected IMerchantService mMerchantService;

    /**
     * The application context.
     */
    protected Context mApplicationContext;

    /**
     * The callback interface to the popup controller.
     */
    protected IPBBAPopupCallback mCallback;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        final Bundle arguments = getArguments();
        mTransaction = (Transaction) arguments.getSerializable(KEY_TRANSACTION);
        mPaymentRequest = (PaymentRequest) arguments.getSerializable(KEY_PAYMENT_REQUEST);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mMerchantService = ZappMerchantServiceFactory.getMerchantService();
        mApplicationContext = activity.getApplicationContext();

        if (activity instanceof IPBBAPopupCallback) {
            mCallback = (IPBBAPopupCallback) activity;
        } else {
            throw new IllegalArgumentException("activity is not IPBBAPopupCallback instance");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //release callback
        mCallback = null;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView closePopupButton = (ImageView) view.findViewById(R.id.pbba_popup_close);
        if (closePopupButton != null) {
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Activity activity = getActivity();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
        }
    }

    /**
     * Get the payment details.
     *
     * @return The {@link com.zapp.core.Transaction payment details}.
     */
    protected Transaction getTransaction() {
        return mTransaction;
    }

    /**
     * Get the payment request details.
     *
     * @return The {@link PaymentRequest} details.
     */
    protected PaymentRequest getPaymentRequest() {
        return mPaymentRequest == null ? mTransaction.getPaymentRequest() : mPaymentRequest;
    }

}
