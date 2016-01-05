package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.ACRType;
import com.zapp.core.Address;
import com.zapp.core.CheckoutType;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryType;
import com.zapp.core.Merchant;
import com.zapp.core.RTPType;
import com.zapp.core.User;
import com.zapp.core.ValidationUtils;
import com.zapp.core.ZappModelValidationException;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;

import uk.co.zapp.samplezappmerchantapp.AddressEditActivity;
import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.ui.AmountEditText;
import uk.co.zapp.samplezappmerchantapp.util.Converter;

/**
 * Deferred payments demo screen.
 */
public class FeatureDemoDeferredFragment extends FeatureDemoFragment {

    /**
     * Log tag.
     */
    public static final String TAG = FeatureDemoDeferredFragment.class.getSimpleName();

    /**
     * Activity result code for address edit.
     */
    public static final int REQUEST_CODE_EDIT_ADDRESS = 10;

    private static final int DELIVERY_TYPE_ADDRESS = 0;

    private static final int DELIVERY_TYPE_COLLECT_IN_STORE = 1;

    private static final int DELIVERY_TYPE_DIGITAL = 2;

    private AmountEditText mAmountInput;

    private AmountEditText mMaxAgreedAmountInput;

    private TextView mDeliveryAddressInput;

    private EditText mEmailInput;

    private View mDeliveryAddressLayout;

    private View mEmailLayout;

    private FrameLayout mInitiatePaymentViewContainer;

    /**
     * Creates a new fragment instance.
     *
     * @param feature Feature which contains the payment request.
     * @return A new fragment instance.
     */
    public static Fragment newInstance(final Serializable feature) {
        final FeatureDemoDeferredFragment fragment = new FeatureDemoDeferredFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_FEATURE, feature);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final int titleResource = mPaymentRequest.getCheckoutType() == CheckoutType.NORMAL ? R.string.app_bar_title_standard_payment
                    : R.string.app_bar_title_pay_plus;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titleResource);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_feature_demo_deferred, container, false);
        final View containerView = view.findViewById(R.id.container);

        mInitiatePaymentViewContainer = (FrameLayout) view.findViewById(R.id.initiate_payment_view_container);

        ViewCompat.setTransitionName(containerView, getString(R.string.transition_container));

        setupFeatureDemo(view);
        return view;
    }

    /**
     * Creates the view with the payment request details.
     *
     * @param view The inflated view for this fragment.
     */
    private void setupFeatureDemo(final View view) {
        try {
            setupDeliveryType(view);
            setupDeliveryAddress(view);
            setupAuthorisationType(view);
            setupAmount(view);
            setupEmail(view);

            mInitiatePaymentViewContainer.addView(createPBBAButton());
        } catch (Exception e) {
            Log.e(TAG, "Failed to set up quick payment feature demo", e);
        }
    }

    /**
     * Creates the ACRType dropdown.
     *
     * @param view The view container.
     */
    private void setupAuthorisationType(final View view) {
        final Spinner spinner = (Spinner) view.findViewById(R.id.authorisation_type);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.authorisation_type, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View v, final int position, final long id) {
                mPaymentRequest.setAcrType(ACRType.values()[position]);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
        spinner.setSelection(mPaymentRequest.getAcrType().ordinal());
    }

    /**
     * Creates the DeliveryType dropdown.
     *
     * @param view The view container.
     */
    private void setupDeliveryType(final View view) {
        final Spinner spinner = (Spinner) view.findViewById(R.id.delivery_type);
        final int deliveryTypeRes = mPaymentRequest.getCheckoutType() == CheckoutType.QUICK ? R.array.delivery_type_pay_plus : R.array.delivery_type_standard;
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), deliveryTypeRes, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View v, final int position, final long id) {

                if (mPaymentRequest.getCheckoutType() == CheckoutType.NORMAL) {
                    switch (position) {
                        case DELIVERY_TYPE_ADDRESS:
                            mPaymentRequest.setDeliveryType(DeliveryType.ADDRESS);
                            mDeliveryAddressLayout.setVisibility(View.VISIBLE);
                            mEmailLayout.setVisibility(View.GONE);
                            setupDeliveryAddress(getView());
                            break;
                        case DELIVERY_TYPE_COLLECT_IN_STORE:
                            mPaymentRequest.setDeliveryType(DeliveryType.COLLECT_IN_STORE);
                            mDeliveryAddressLayout.setVisibility(View.VISIBLE);
                            mEmailLayout.setVisibility(View.GONE);
                            setupDeliveryAddress(getView());
                            break;
                        case DELIVERY_TYPE_DIGITAL:
                            mPaymentRequest.setDeliveryType(DeliveryType.DIGITAL);
                            mDeliveryAddressLayout.setVisibility(View.GONE);
                            mEmailLayout.setVisibility(View.VISIBLE);
                            setupEmail(getView());
                            break;
                    }

                } else {
                    final DeliveryType deliveryType = position == 0 ? DeliveryType.ADDRESS : DeliveryType.DIGITAL;
                    mPaymentRequest.setDeliveryType(deliveryType);
                    view.findViewById(R.id.delivery_address_bottom_divider).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // use the default value, no implementation requires.
            }
        });

        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        if (mPaymentRequest.getCheckoutType() == CheckoutType.NORMAL) {
            switch (deliveryType) {
                case ADDRESS:
                    spinner.setSelection(DELIVERY_TYPE_ADDRESS);
                    break;
                case COLLECT_IN_STORE:
                    spinner.setSelection(DELIVERY_TYPE_COLLECT_IN_STORE);
                    break;
                case DIGITAL:
                    spinner.setSelection(DELIVERY_TYPE_DIGITAL);
                    break;
            }
        } else {
            if (deliveryType == DeliveryType.ADDRESS) {
                spinner.setSelection(DELIVERY_TYPE_ADDRESS);
            } else if (deliveryType == DeliveryType.DIGITAL) {
                spinner.setSelection(DELIVERY_TYPE_DIGITAL);
            }
        }
    }

    /**
     * Creates the Delivery Address dropdown.
     *
     * @param view The view container.
     */
    private void setupDeliveryAddress(final View view) {
        mDeliveryAddressInput = (TextView) view.findViewById(R.id.delivery_address);
        mDeliveryAddressLayout = view.findViewById(R.id.delivery_address_input_layout);
        if (isAddressFieldsRequired()) {
            mDeliveryAddressLayout.setVisibility(View.VISIBLE);

            final User user = mPaymentRequest.getUser();
            if (mPaymentRequest.getDeliveryType() == DeliveryType.ADDRESS && mPaymentRequest.getAddress() != null) {

                final Address customerAddress = mFeature.getCustomerAddress();
                mPaymentRequest.setAddress(customerAddress);

                final StringBuilder builder = new StringBuilder(user.getFirstName());
                if (!TextUtils.isEmpty(user.getLastName())) {
                    builder.append(" ").append(user.getLastName());
                }

                mDeliveryAddressInput.setText(getFormattedAddress(builder.toString(), customerAddress));

                mDeliveryAddressLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final Intent editIntent = new Intent(getActivity(), AddressEditActivity.class);
                        editIntent.putExtra(AddressEditActivity.KEY_MODEL_USER, user);
                        editIntent.putExtra(AddressEditActivity.KEY_MODEL_ADDRESS, customerAddress);
                        startActivityForResult(editIntent, REQUEST_CODE_EDIT_ADDRESS);
                    }

                });
            } else if (mPaymentRequest.getDeliveryType() == DeliveryType.COLLECT_IN_STORE) {
                final Merchant merchant = mPaymentRequest.getMerchant();
                final Address merchantStoreAddress = mFeature.getMerchantStoreAddress();
                mPaymentRequest.setAddress(merchantStoreAddress);

                mDeliveryAddressInput.setText(getFormattedAddress(merchant.getName(), merchantStoreAddress));

                //merchant's delivery address cannot be changed
                mDeliveryAddressLayout.setOnClickListener(null);
            }
        }
    }

    /**
     * Return a formatted address prepended by the given label.
     *
     * @param label   The label to use.
     * @param address The address to use.
     * @return The formatted string.
     */
    private String getFormattedAddress(final String label, final Address address) {
        final StringBuilder builder = new StringBuilder(label);

        if (!TextUtils.isEmpty(address.getLine1())) {
            builder.append(", ").append(address.getLine1());
        }
        if (!TextUtils.isEmpty(address.getLine2())) {
            builder.append(", ").append(address.getLine2());
        }
        if (!TextUtils.isEmpty(address.getLine3())) {
            builder.append(", ").append(address.getLine3());
        }
        if (!TextUtils.isEmpty(address.getLine4())) {
            builder.append(", ").append(address.getLine4());
        }
        if (!TextUtils.isEmpty(address.getLine5())) {
            builder.append(", ").append(address.getLine5());
        }
        if (!TextUtils.isEmpty(address.getLine6())) {
            builder.append(", ").append(address.getLine6());
        }
        if (!TextUtils.isEmpty(address.getPostCode())) {
            builder.append(", ").append(address.getPostCode());
        }
        if (!TextUtils.isEmpty(address.getCountryCode())) {
            builder.append(", ").append(address.getCountryCode());
        }

        return builder.toString();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_ADDRESS && data != null) {
            final String firstName = data.getStringExtra(AddressEditActivity.KEY_FIRST_NAME);
            final String lastName = data.getStringExtra(AddressEditActivity.KEY_LAST_NAME);
            final Address address = (Address) data.getSerializableExtra(AddressEditActivity.KEY_MODEL_ADDRESS);
            mPaymentRequest.setAddress(address);
            final User user = mPaymentRequest.getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            mPaymentRequest.setUser(user);

            final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
            switch (deliveryType) {
                case COLLECT_IN_STORE:
                    mFeature.setMerchantStoreAddress(address);
                    break;
                default:
                    mFeature.setCustomerAddress(address);
                    break;
            }

            setupFeatureDemo(getView());
        }
    }


    /**
     * Populates the amount field.
     *
     * @param view The view container.
     */
    private void setupAmount(final View view) {

        mAmountInput = (AmountEditText) view.findViewById(R.id.amount);
        mMaxAgreedAmountInput = (AmountEditText) view.findViewById(R.id.max_agreed_amount);

        final Long amount;
        if (mPaymentRequest.getRtpType() == RTPType.IMMEDIATE) {
            amount = mPaymentRequest.getAmount().getValue();
            mMaxAgreedAmountInput.setVisibility(View.GONE);
        } else {
            amount = mPaymentRequest.getDefrdRTPAgrmtAmount().getValue();
            if (mMaxAgreedAmountInput.getSimpleText() == null) {
                final CurrencyAmount defrdRTPMaxAgrdAmount = mPaymentRequest.getDefrdRTPMaxAgrdAmount();
                final String maxAmountText = defrdRTPMaxAgrdAmount == null ? AmountEditText.PLACEHOLDER : Converter.toStringAmount(defrdRTPMaxAgrdAmount.getValue());
                mMaxAgreedAmountInput.setIsPlaceholderRequired(true);
                mMaxAgreedAmountInput.setText(maxAmountText);
                mMaxAgreedAmountInput.addPoundPrefix();
            }
        }
        if (mAmountInput.getSimpleText() == null) {
            mAmountInput.setText(Converter.toStringAmount(amount));
            mAmountInput.addPoundPrefix();
        }
    }

    /**
     * Populates the email field for DeliveryType.DIGITAL or hides it otherwise.
     *
     * @param view The view container.
     */
    private void setupEmail(final View view) {
        mEmailInput = (EditText) view.findViewById(R.id.email);
        mEmailLayout = view.findViewById(R.id.email_input_layout);

        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        final CheckoutType checkoutType = mPaymentRequest.getCheckoutType();
        if (deliveryType == DeliveryType.DIGITAL && checkoutType == CheckoutType.NORMAL) {
            mEmailLayout.setVisibility(View.VISIBLE);

            final User user = mPaymentRequest.getUser();
            if (user != null) {
                mEmailInput.setText(user.getEmail());
            }
        }
    }

    /**
     * Checks if delivery address input fields should be displayed.
     *
     * @return true if delivery address input fields should be displayed.
     */
    private boolean isAddressFieldsRequired() {
        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        return mPaymentRequest.getCheckoutType() == CheckoutType.NORMAL && (deliveryType == DeliveryType.ADDRESS || deliveryType == DeliveryType.COLLECT_IN_STORE);
    }

    /**
     * Updates feature.
     */
    protected boolean updateFeature() {
        try {
            updateAmount();
            updateEmail();

            Features.getInstance(getActivity().getApplicationContext()).updateFeature(mFeature);
        } catch (Exception e) {
            if (mInitiatePaymentButton != null) {
                mInitiatePaymentButton.stopAnimation();
                mInitiatePaymentButton.setEnabled(true);
            }
            showErrorMessage(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Updates the amount for RTPType.IMMEDIATE and defrdRTPAgrmtAmount, defrdRTPMaxAgrdAmount for RTPType.DEFERRED payments.
     */
    private void updateAmount() throws ZappModelValidationException {
        final String amountText = mAmountInput.getSimpleText();
        final String maxAmountText = mMaxAgreedAmountInput.getSimpleText();
        if (TextUtils.isEmpty(amountText)) {
            throw new NumberFormatException(getString(R.string.feature_edit_no_amount_value_error));
        }

        if (mPaymentRequest.getRtpType() == RTPType.IMMEDIATE) {
            mPaymentRequest.setAmount(formatAmount(amountText));
        } else {
            final CurrencyAmount amount = formatAmount(amountText);
            mPaymentRequest.setDefrdRTPAgrmtAmount(amount);

            //Max agreed amount is optional. If it is present then it's value should be higher than amount.
            final CurrencyAmount maxAmount = TextUtils.isEmpty(maxAmountText) ? null : formatAmount(maxAmountText);
            if (maxAmount == null || maxAmount.getValue() >= amount.getValue()) {
                mPaymentRequest.setDefrdRTPMaxAgrdAmount(maxAmount);
            } else {
                throw new NumberFormatException(getString(R.string.feature_edit_low_max_amount_error));
            }
        }
    }

    /**
     * Ensure that the number has two digits after decimal point required by the Converter.
     *
     * @param amountText The amount text from the input text field to be converted to a valid {@link CurrencyAmount}.
     * @return The {@link CurrencyAmount} model with the amountText value.
     * @throws ZappModelValidationException exception thrown on CurrencyAmount creation.
     */
    @NonNull
    private CurrencyAmount formatAmount(@NonNull final String amountText) throws ZappModelValidationException {
        final Long value;
        try {
            value = Converter.fromStringAmount(amountText);
        } catch (IllegalArgumentException e) {
            throw new NumberFormatException(getString(R.string.feature_edit_invalid_amount_value_error));
        }
        return new CurrencyAmount(value, CurrencyAmount.POUNDS);
    }

    /**
     * Updates the consumers email.
     */
    private void updateEmail() throws ZappModelValidationException {
        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        final CheckoutType checkoutType = mPaymentRequest.getCheckoutType();

        final User user = mPaymentRequest.getUser();

        if (deliveryType == DeliveryType.DIGITAL && checkoutType == CheckoutType.NORMAL && user != null) {
            final String email = mEmailInput.getText().toString();
            ValidationUtils.requireValidEmail(email, getString(R.string.error_invalid_email));
            user.setEmail(email);
        }
    }

    /**
     * Displays a dialog with a error message.
     *
     * @param message The error message.
     */
    private void showErrorMessage(final CharSequence message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.feature_edit_error_dialog_title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.feature_edit_error_dialog_positive_button, null);
        builder.create().show();
    }

    @Override
    public void onClick(final View v) {
        if (updateFeature()) {
            super.onClick(v);
        }
    }
}
