package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.Address;
import com.zapp.core.CheckoutType;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryType;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.Serializable;

import uk.co.zapp.samplezappmerchantapp.R;

/**
 * Immediate payments demo screen.
 */
public class FeatureDemoImmediateFragment extends FeatureDemoFragment {

    /**
     * Log tag.
     */
    public static final String TAG = FeatureDemoImmediateFragment.class.getSimpleName();

    private TextView mTitleTextView;

    private TextView mDescriptionTextView;

    private TextView mAmountTextView;

    private TextView mBillReferenceView;

    private TextView mConsumerNameView;

    private FrameLayout mInitatePaymentViewContainer;

    private TextView mAddressLine1View;

    private TextView mAddressLine2View;

    private TextView mAddressLine3View;

    private TextView mAddressLine4View;

    private TextView mAddressLine5View;

    private TextView mAddressLine6View;

    private TextView mPostCodeView;

    private TextView mCountryCodeView;

    private TextView mEmail;

    /**
     * Creates a new fragment instance.
     *
     * @param feature Feature which contains the payment request.
     * @return A new fragment instance.
     */
    public static Fragment newInstance(final Serializable feature) {
        final FeatureDemoImmediateFragment fragment = new FeatureDemoImmediateFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_FEATURE, feature);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_feature_demo_immediate, container, false);
        final View containerView = view.findViewById(R.id.container);

        mTitleTextView = (TextView) view.findViewById(R.id.title_text_view);
        mDescriptionTextView = (TextView) view.findViewById(R.id.description_text_view);
        mAmountTextView = (TextView) view.findViewById(R.id.amount_text_view);

        mEmail = (TextView) view.findViewById(R.id.email);
        mBillReferenceView = (TextView) view.findViewById(R.id.bill_reference);
        mConsumerNameView = (TextView) view.findViewById(R.id.consumer_name);
        mAddressLine1View = (TextView) view.findViewById(R.id.address_line_1);
        mAddressLine2View = (TextView) view.findViewById(R.id.address_line_2);
        mAddressLine3View = (TextView) view.findViewById(R.id.address_line_3);
        mAddressLine4View = (TextView) view.findViewById(R.id.address_line_4);
        mAddressLine5View = (TextView) view.findViewById(R.id.address_line_5);
        mAddressLine6View = (TextView) view.findViewById(R.id.address_line_6);
        mPostCodeView = (TextView) view.findViewById(R.id.post_code);
        mCountryCodeView = (TextView) view.findViewById(R.id.country_code);

        mInitatePaymentViewContainer = (FrameLayout) view.findViewById(R.id.initiate_payment_view_container);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.feature_demo_container, FeatureEditImmediateFragment.newInstance(mFeature), FeatureEditImmediateFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ViewCompat.setTransitionName(containerView, getString(R.string.transition_container));

        setupFeatureDemo();

        return view;
    }

    private void setupFeatureDemo() {
        try {
            final CheckoutType checkoutType = mPaymentRequest.getCheckoutType();
            final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();

            mTitleTextView.setText(mFeature.getTitle());
            mDescriptionTextView.setText(mFeature.getDescription());

            switch (deliveryType) {
                case COLLECT_IN_STORE:
                case ADDRESS:
                    if (checkoutType == CheckoutType.NORMAL && mPaymentRequest.getPaymentType() != PaymentType.BILL_PAY) {
                        showDeliveryAddress();
                    }
                    break;
                case DIGITAL:
                    if (checkoutType == CheckoutType.NORMAL) {
                        final User user = mPaymentRequest.getUser();
                        if (user != null) {
                            mEmail.setVisibility(View.VISIBLE);
                            mEmail.setText(user.getEmail());
                        }
                    }
                    break;
                default:
                    //no specific implementation for other delivery types required
                    break;
            }

            if (mPaymentRequest.getPaymentType() == PaymentType.BILL_PAY) {
                mBillReferenceView.setVisibility(View.VISIBLE);
                mBillReferenceView.setText(getString(R.string.feature_demo_immediate_bill_reference, mPaymentRequest.getBillDetails().getReference()));
            }

            final CurrencyAmount amount = mPaymentRequest.getRtpType() == RTPType.IMMEDIATE ? mPaymentRequest.getAmount() : mPaymentRequest.getDefrdRTPAgrmtAmount();
            mAmountTextView.setText(amount.toString());

            mInitatePaymentViewContainer.addView(createPBBAButton());
        } catch (Exception e) {
            Log.e(TAG, "Failed to set up quick payment feature demo", e);
        }
    }

    /**
     * Shows the users delivery address.
     */
    public void showDeliveryAddress() {
        final User user = mPaymentRequest.getUser();
        final Address deliveryAddress = mPaymentRequest.getAddress();
        if (deliveryAddress != null && user != null) {
            StringBuilder consumerName = new StringBuilder();
            consumerName.append(user.getFirstName());
            if (!TextUtils.isEmpty(user.getLastName())) {
                if (!TextUtils.isEmpty(consumerName)) {
                    consumerName.append(" ");
                }
                consumerName.append(user.getLastName());
            }

            showAddressInfo(consumerName.toString(), mConsumerNameView);
            showAddressInfo(deliveryAddress.getLine1(), mAddressLine1View);
            showAddressInfo(deliveryAddress.getLine2(), mAddressLine2View);
            showAddressInfo(deliveryAddress.getLine3(), mAddressLine3View);
            showAddressInfo(deliveryAddress.getLine4(), mAddressLine4View);
            showAddressInfo(deliveryAddress.getLine5(), mAddressLine5View);
            showAddressInfo(deliveryAddress.getLine6(), mAddressLine6View);
            showAddressInfo(deliveryAddress.getPostCode(), mPostCodeView);
            showAddressInfo(deliveryAddress.getCountryCode(), mCountryCodeView);
        }
    }

    /**
     * Sets the given text in to view and makes it visible.
     *
     * @param text The text to be displayed.
     * @param view The view which displays the text.
     */
    private void showAddressInfo(final String text, final TextView view) {
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
        }
    }
}
