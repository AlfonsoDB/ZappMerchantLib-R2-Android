package uk.co.zapp.samplezappmerchantapp;

import com.zapp.core.Address;
import com.zapp.core.User;
import com.zapp.core.ZappModelValidationException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import uk.co.zapp.samplezappmerchantapp.ui.fragment.FeatureDemoDeferredFragment;

/**
 * Activity to edit the delivery address attributes.
 *
 * @author msagi
 */
public class AddressEditActivity extends AbstractActivity implements View.OnClickListener {

    /**
     * Key for intent extra 'user';
     */
    public static final String KEY_MODEL_USER = "key.user";

    /**
     * Key for intent extra 'address';
     */
    public static final String KEY_MODEL_ADDRESS = "key.address";

    /**
     * Key for intent extra 'firstName';
     */
    public static final String KEY_FIRST_NAME = "key.first_name";

    /**
     * Key for intent extra 'lastName';
     */
    public static final String KEY_LAST_NAME = "key.last_name";

    private EditText mAddressLine1;

    private EditText mAddressLine2;

    private EditText mAddressLine3;

    private EditText mAddressLine4;

    private EditText mAddressLine5;

    private EditText mAddressLine6;

    private EditText mPostCode;

    private EditText mCountryCode;

    private Address mAddress;

    private EditText mFirstNameInput;

    private EditText mLastNameInput;

    private String mFirstName;

    private String mLastName;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feature_edit_address);
        setActionBar();

        final User user = (User) getIntent().getSerializableExtra(KEY_MODEL_USER);
        mAddress = (Address) getIntent().getSerializableExtra(KEY_MODEL_ADDRESS);

        mFirstNameInput = (EditText) findViewById(R.id.first_name);
        mLastNameInput = (EditText) findViewById(R.id.last_name);
        mAddressLine1 = (EditText) findViewById(R.id.address_line_1);
        mAddressLine2 = (EditText) findViewById(R.id.address_line_2);
        mAddressLine3 = (EditText) findViewById(R.id.address_line_3);
        mAddressLine4 = (EditText) findViewById(R.id.address_line_4);
        mAddressLine5 = (EditText) findViewById(R.id.address_line_5);
        mAddressLine6 = (EditText) findViewById(R.id.address_line_6);
        mPostCode = (EditText) findViewById(R.id.postcode);
        mCountryCode = (EditText) findViewById(R.id.country_code);

        findViewById(R.id.ok_btn).setOnClickListener(this);
        findViewById(R.id.cancel_btn).setOnClickListener(this);

        mFirstNameInput.setText(user.getFirstName());
        mLastNameInput.setText(user.getLastName());
        mAddressLine1.setText(mAddress.getLine1());
        mAddressLine2.setText(mAddress.getLine2());
        mAddressLine3.setText(mAddress.getLine3());
        mAddressLine4.setText(mAddress.getLine4());
        mAddressLine5.setText(mAddress.getLine5());
        mAddressLine6.setText(mAddress.getLine6());
        mPostCode.setText(mAddress.getPostCode());
        mCountryCode.setText(mAddress.getCountryCode());
    }

    /**
     * Updates the feature.
     */
    protected void updateFeature() {
        try {
            updateDeliveryAddress();

            final Intent intent = new Intent();
            intent.putExtra(KEY_MODEL_ADDRESS, mAddress);
            intent.putExtra(KEY_FIRST_NAME, mFirstName);
            intent.putExtra(KEY_LAST_NAME, mLastName);
            setResult(FeatureDemoDeferredFragment.REQUEST_CODE_EDIT_ADDRESS, intent);

            finish();
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Updates delivery address.
     */
    private void updateDeliveryAddress() throws ZappModelValidationException {
        final String countryCode = getInputValue(mCountryCode);
        mFirstName = getInputValue(mFirstNameInput);
        mLastName = getInputValue(mLastNameInput);

        if (TextUtils.isEmpty(countryCode)) {
            throw new ZappModelValidationException(getString(R.string.feature_edit_error_missing_country_code));
        }

        if (TextUtils.isEmpty(mFirstName)) {
            throw new ZappModelValidationException(getString(R.string.feature_edit_error_missing_first_name));
        }
        if (TextUtils.isEmpty(mLastName)) {
            throw new ZappModelValidationException(getString(R.string.feature_edit_error_missing_last_name));
        }

        mAddress = new Address(getInputValue(mAddressLine1)
                , getInputValue(mAddressLine2)
                , getInputValue(mAddressLine3)
                , getInputValue(mAddressLine4)
                , getInputValue(mAddressLine5)
                , getInputValue(mAddressLine6)
                , getInputValue(mPostCode)
                , countryCode);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.feature_edit_error_dialog_title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.feature_edit_error_dialog_positive_button, null);
        builder.show();
    }

    @Override
    public void onClick(final View v) {
        final int id = v.getId();
        if (id == R.id.ok_btn) {
            updateFeature();
        } else if (id == R.id.cancel_btn) {
            finish();
        }
    }
}
