package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.Address;
import com.zapp.core.BillDetails;
import com.zapp.core.CheckoutType;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryType;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.User;
import com.zapp.core.ValidationUtils;
import com.zapp.core.ZappModelValidationException;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Feature;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.util.Converter;

/**
 * Fragment to edit immediate payment feature attributes.
 *
 * @author msagi
 */
public class FeatureEditImmediateFragment extends Fragment {

    /**
     * Key for intent extra 'feature';
     */
    private static final String KEY_FEATURE = "key_feature";

    /**
     * Tag for logging.
     */
    public static final String TAG = FeatureEditImmediateFragment.class.getSimpleName();

    private EditText mAmountInput;

    private EditText mBillReferenceInput;

    private EditText mEmailInput;

    private EditText mAddressLine1;

    private EditText mAddressLine2;

    private EditText mAddressLine3;

    private EditText mAddressLine4;

    private EditText mAddressLine5;

    private EditText mAddressLine6;

    private EditText mPostCode;

    private EditText mCountryCode;

    private Feature mFeature;

    private PaymentRequest mPaymentRequest;

    /**
     * Creates a new fragment instance.
     *
     * @param feature Feature which contains the payment request.
     * @return A new fragment instance.
     */
    public static Fragment newInstance(final Feature feature) {
        final FeatureEditImmediateFragment fragment = new FeatureEditImmediateFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_FEATURE, feature);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFeature = (Feature) getArguments().getSerializable(KEY_FEATURE);
        if (mFeature != null) {
            mPaymentRequest = mFeature.getPaymentRequest();
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_feature_edit_immediate, container, false);
        mAmountInput = (EditText) view.findViewById(R.id.amount);

        final Long amount;
        if (mPaymentRequest.getRtpType() == RTPType.IMMEDIATE) {
            amount = mPaymentRequest.getAmount().getValue();
        } else {
            amount = mPaymentRequest.getDefrdRTPAgrmtAmount().getValue();
        }
        mAmountInput.setText(Converter.toStringAmount(amount));

        setupBillReference(view);
        setupEmail(view);
        setupAddress(view);
        return view;
    }

    /**
     * Populates the bill reference number field for PaymentType.BILL_PAY or hides it otherwise.
     */
    private void setupBillReference(final View view) {
        mBillReferenceInput = (EditText) view.findViewById(R.id.bill_reference);

        if (mPaymentRequest.getPaymentType() == PaymentType.BILL_PAY) {
            final BillDetails billDetails = mPaymentRequest.getBillDetails();
            if (billDetails != null) {
                mBillReferenceInput.setText(billDetails.getReference());
            }
        } else {
            view.findViewById(R.id.bill_reference_label).setVisibility(View.GONE);
            mBillReferenceInput.setVisibility(View.GONE);
        }
    }

    /**
     * Populates the email field for DeliveryType.DIGITAL or hides it otherwise.
     */
    private void setupEmail(final View view) {
        mEmailInput = (EditText) view.findViewById(R.id.email);

        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        if (deliveryType == DeliveryType.DIGITAL) {

            final User user = mPaymentRequest.getUser();
            if (user != null) {
                mEmailInput.setText(user.getEmail());
            }
        } else {
            view.findViewById(R.id.email_label).setVisibility(View.GONE);
            mEmailInput.setVisibility(View.GONE);
        }
    }

    /**
     * Populates the address fields for CheckoutType.NORMAL and DeliveryType.ADDRESS or hides them otherwise.
     */
    private void setupAddress(final View view) {
        mAddressLine1 = (EditText) view.findViewById(R.id.address_line_1);
        mAddressLine2 = (EditText) view.findViewById(R.id.address_line_2);
        mAddressLine3 = (EditText) view.findViewById(R.id.address_line_3);
        mAddressLine4 = (EditText) view.findViewById(R.id.address_line_4);
        mAddressLine5 = (EditText) view.findViewById(R.id.address_line_5);
        mAddressLine6 = (EditText) view.findViewById(R.id.address_line_6);
        mPostCode = (EditText) view.findViewById(R.id.postcode);
        mCountryCode = (EditText) view.findViewById(R.id.country_code);

        if (isAddressFieldsRequired()) {
            final Address address = mPaymentRequest.getAddress();
            mAddressLine1.setText(address.getLine1());
            mAddressLine2.setText(address.getLine2());
            mAddressLine3.setText(address.getLine3());
            mAddressLine4.setText(address.getLine4());
            mAddressLine5.setText(address.getLine5());
            mAddressLine6.setText(address.getLine6());
            mPostCode.setText(address.getPostCode());
            mCountryCode.setText(address.getCountryCode());
        } else {
            view.findViewById(R.id.feature_edit_address_label).setVisibility(View.GONE);
            view.findViewById(R.id.feature_edit_post_code_label).setVisibility(View.GONE);
            view.findViewById(R.id.feature_edit_country_code_label).setVisibility(View.GONE);
            mAddressLine1.setVisibility(View.GONE);
            mAddressLine2.setVisibility(View.GONE);
            mAddressLine3.setVisibility(View.GONE);
            mAddressLine4.setVisibility(View.GONE);
            mAddressLine5.setVisibility(View.GONE);
            mAddressLine6.setVisibility(View.GONE);
            mPostCode.setVisibility(View.GONE);
            mCountryCode.setVisibility(View.GONE);
        }
    }

    /**
     * Checks if delivery address input fields should be displayed.
     *
     * @return true if delivery address input fields should be displayed.
     */
    private boolean isAddressFieldsRequired() {
        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        return mPaymentRequest.getCheckoutType() == CheckoutType.NORMAL && deliveryType == DeliveryType.ADDRESS && mPaymentRequest.getAddress() != null;
    }

    /**
     * Updates feature.
     */
    protected void updateFeature() {
        try {
            updateAmount();
            updateBillReference();
            updateEmail();
            updateAddress();

            Features.getInstance(getActivity().getApplicationContext()).updateFeature(mFeature);
            hideSoftKeyboard();
            getFragmentManager().popBackStack();
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Hides the soft keyboard.
     */
    private void hideSoftKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mAmountInput.getWindowToken(), 0);
    }

    /**
     * Updates the amount for payment request.
     */
    private void updateAmount() throws ZappModelValidationException {
        final String amountText = mAmountInput.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            throw new NumberFormatException(getString(R.string.feature_edit_no_amount_value_error));
        }
        final Long value;
        try {
            value = Converter.fromStringAmount(amountText);
        } catch (IllegalArgumentException e) {
            throw new NumberFormatException(getString(R.string.feature_edit_invalid_amount_value_error));
        }
        final CurrencyAmount amount = new CurrencyAmount(value, CurrencyAmount.POUNDS);

        if (mPaymentRequest.getRtpType() == RTPType.IMMEDIATE) {
            mPaymentRequest.setAmount(amount);
        } else {
            mPaymentRequest.setDefrdRTPAgrmtAmount(amount);
        }
    }

    /**
     * Updates the bill reference.
     */
    private void updateBillReference() {
        final PaymentType paymentType = mPaymentRequest.getPaymentType();
        final BillDetails billDetails = mPaymentRequest.getBillDetails();

        if (paymentType == PaymentType.BILL_PAY && billDetails != null) {
            final String referenceNumber = getInputValue(mBillReferenceInput);
            billDetails.setReference(referenceNumber);
        }
    }

    /**
     * Updates the consumers email.
     */
    private void updateEmail() throws ZappModelValidationException {
        final DeliveryType deliveryType = mPaymentRequest.getDeliveryType();
        final User user = mPaymentRequest.getUser();

        if (deliveryType == DeliveryType.DIGITAL && user != null) {
            final String target = mEmailInput.getText().toString();
            ValidationUtils.requireValidEmail(target, getString(R.string.error_invalid_email));
            user.setEmail(target);
        }
    }

    /**
     * Updates delivery address.
     */
    private void updateAddress() throws ZappModelValidationException {
        if (isAddressFieldsRequired()) {
            final Address address = new Address(getInputValue(mAddressLine1)
                    , getInputValue(mAddressLine2)
                    , getInputValue(mAddressLine3)
                    , getInputValue(mAddressLine4)
                    , getInputValue(mAddressLine5)
                    , getInputValue(mAddressLine6)
                    , getInputValue(mPostCode)
                    , getInputValue(mCountryCode));

            mPaymentRequest.setAddress(address);
        }
    }

    /**
     * Checks if the address line is empty then returns null.
     *
     * @param addressLine The adress line.
     * @return null if the address line value is null or empty.
     */
    private String getInputValue(final EditText addressLine) {
        final String line = addressLine.getText().toString();
        return TextUtils.isEmpty(line) ? null : line;
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
        builder.show();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.feature_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_save) {
            updateFeature();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
