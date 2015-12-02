package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.PaymentRequest;
import com.zapp.core.Transaction;
import com.zapp.library.merchant.model.callback.ProcessResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;
import com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate;
import com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate;
import com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory;
import com.zapp.library.merchant.service.provider.IMerchantService;
import com.zapp.library.merchant.view.PBBAButton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Feature;
import uk.co.zapp.samplezappmerchantapp.network.delegate.MerchantNetworkServiceDelegateImpl;
import uk.co.zapp.samplezappmerchantapp.ui.MerchantUIDelegateImpl;

/**
 * Feature demo base class.
 */
public abstract class FeatureDemoFragment extends Fragment implements View.OnClickListener {

    /**
     * Log tag.
     */
    private static final String TAG = FeatureDemoFragment.class.getSimpleName();

    /**
     * Key for arguments 'feature';
     */
    protected static final String KEY_FEATURE = "key_feature";

    /**
     * The merchant service.
     */
    protected IMerchantService mMerchantService;

    /**
     * Zapp merchant UI delegate.
     */
    protected ZappMerchantUIDelegate mUiDelegate;

    /**
     * Merchant network service delegate.
     */
    protected IMerchantNetworkServiceDelegate mServiceDelegate;

    /**
     * The view to activate the payment journey.
     */
    protected PBBAButton mInitiatePaymentButton;

    protected PaymentRequest mPaymentRequest;

    protected Feature mFeature;

    /**
     * Response listener for the 'initiate payment' flow.
     */
    protected ProcessResponseListener<Transaction> mInitiatePaymentResponseListener = new ProcessResponseListener<Transaction>() {
        @Override
        public void onFailure(final com.zapp.library.merchant.exception.Error error) {
            mInitiatePaymentButton.stopAnimation();
            Log.e(TAG, "Initiate payment response listener .onFailure");
        }

        @Override
        public void onSuccess(final ResponseWrapper<Transaction> response) {
            mInitiatePaymentButton.stopAnimation();
            Log.i(TAG, "Initiate payment response listener .onSuccess");
        }
    };

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeature = (Feature) getArguments().getSerializable(KEY_FEATURE);
        if (mFeature != null) {
            mPaymentRequest = mFeature.getPaymentRequest();

            mServiceDelegate = new MerchantNetworkServiceDelegateImpl(getActivity());
            mUiDelegate = new MerchantUIDelegateImpl(getActivity());
            mMerchantService = ZappMerchantServiceFactory.createMerchantService(mServiceDelegate, mUiDelegate);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //the Pay by Bank app button automatically disables itself on click to avoid multiple clicks
        //here we enable the button again if the user taps back on the popup then he should be able to tap on the button again.
        if (mInitiatePaymentButton != null) {
            mInitiatePaymentButton.setEnabled(true);
        }
    }

    /**
     * Dynamically creates a {@link PBBAButton}.
     */
    protected PBBAButton createPBBAButton() {
        mInitiatePaymentButton = new PBBAButton(getActivity());
        mInitiatePaymentButton.setId(R.id.pbba_button);
        mInitiatePaymentButton.setOnClickListener(this);
        return mInitiatePaymentButton;
    }

    @Override
    public void onClick(final View v) {
        Log.i(TAG, "Initiating payment...");
        mMerchantService.initiatePayment(getActivity().getApplicationContext(), mPaymentRequest, mInitiatePaymentResponseListener);
    }
}
