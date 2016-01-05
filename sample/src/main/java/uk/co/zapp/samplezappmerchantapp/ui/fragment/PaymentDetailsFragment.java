package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.Address;
import com.zapp.core.DeliveryType;
import com.zapp.core.PaymentAuth;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.core.User;
import com.zapp.library.merchant.model.SettlementStatus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.util.StringUtils;

/**
 * The payment confirmation details screen.
 *
 * @author nsevciuc
 */
public class PaymentDetailsFragment extends BasePaymentFragment {

    /**
     * Log tag.
     */
    private static final String TAG = PaymentDetailsFragment.class.getSimpleName();

    /**
     * The view for displaying payment full details.
     */
    private TextView mPaymentDetailsTextView;

    /**
     * Creates a new instance of {@link PaymentDetailsFragment}
     *
     * @param transaction      The payment transaction argument.
     * @param settlementStatus The payment transaction settlement status
     * @return The fragment instance.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public static Fragment newInstance(final Transaction transaction, final SettlementStatus settlementStatus) {
        final Fragment fragment = new PaymentDetailsFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_TRANSACTION, transaction);
        args.putSerializable(KEY_SETTLEMENT_STATUS, settlementStatus);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new instance of {@link PaymentDetailsFragment}
     *
     * @param transaction The payment transaction argument.
     * @return The fragment instance.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public static Fragment newInstance(final Transaction transaction) {
        return newInstance(transaction, null);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_confirmed_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPaymentDetailsTextView = (TextView) view.findViewById(R.id.payment_confirmation_details);
        displayOrderDetails();
    }

    /**
     * Fills payment details view with full order information.
     */
    private void displayOrderDetails() {
        final StringBuilder descriptionTextBuilder = new StringBuilder();
        final PaymentRequest paymentRequest = mTransaction.getPaymentRequest();
        final RTPType rtpType = paymentRequest.getRtpType();
        final PaymentType paymentType = paymentRequest.getPaymentType();
        final TransactionId transactionId = mTransaction.getTransactionId();
        final String aptId = transactionId == null ? getString(R.string.transaction_details_not_available) : transactionId.getAptId();
        final DeliveryType deliveryType = paymentRequest.getDeliveryType();

        final String status = mSettlementStatus != null ? mSettlementStatus.toString() : mTransaction.getTransactionStatus().toString();

        descriptionTextBuilder.append(getString(R.string.transaction_details, status, mTransaction.getBrn(), aptId, StringUtils.toHumanReadableString(rtpType),
                StringUtils.toHumanReadableString(paymentType), StringUtils.toHumanReadableString(paymentRequest.getCheckoutType()),
                StringUtils.toHumanReadableString(deliveryType)));

        if (paymentType == PaymentType.BILL_PAY) {
            descriptionTextBuilder.append(getString(R.string.transaction_bill_details, paymentRequest.getBillDetails().getReference()));
        }

        displayDeliveryAddress(descriptionTextBuilder, deliveryType, mTransaction.getPaymentAuth());

        if (rtpType == RTPType.DEFERRED) {
            descriptionTextBuilder.append(getString(R.string.transaction_details_deferred_payment_msg));
        }

        mPaymentDetailsTextView.setText(Html.fromHtml(descriptionTextBuilder.toString()));
    }

    /**
     * Fills payment details view with delivery address information.
     *
     * @param descriptionTextBuilder The string builder that contains order details information.
     * @param deliveryType           The payment delivery type.
     * @param paymentAuth            The payment authorisation.
     */
    private void displayDeliveryAddress(final StringBuilder descriptionTextBuilder, final DeliveryType deliveryType, final PaymentAuth paymentAuth) {
        //TODO as PaymentAuth does not contain the SettlementStatus, it cannot be used for payment authorisation for immediate payments,
        //TODO retrieve the shipping address from PaymentRequest.
        final User user = paymentAuth != null ? paymentAuth.getUser() : mTransaction.getPaymentRequest().getUser();
        final Address deliveryAddress = paymentAuth != null ? paymentAuth.getDeliveryAddress() : mTransaction.getPaymentRequest().getAddress();
        switch (deliveryType) {
            case ADDRESS:
            case COLLECT_IN_STORE:
            case SERVICE:
                displayPhysicalDeliveryAddress(descriptionTextBuilder, deliveryAddress, user);
                break;
            case DIGITAL:
                displayDigitalDeliveryAddress(descriptionTextBuilder, user);
                break;
            case FACE_2_FACE:
                // No delivery address information should be shown for Face to Face delivery type.
                descriptionTextBuilder.append(getString(R.string.transaction_details_face_to_face__delivery_address));
                break;
            default:
                descriptionTextBuilder.append(getString(R.string.transaction_details_unknown_delivery_type));
                Log.e(TAG, descriptionTextBuilder.toString());
                break;
        }
    }

    /**
     * Fills payment details view with delivery physical address information.
     *
     * @param descriptionTextBuilder The string builder that contains order details information.
     * @param deliveryAddress                The delivery address.
     * @param user                   The consumer.
     */
    private void displayPhysicalDeliveryAddress(final StringBuilder descriptionTextBuilder, final Address deliveryAddress, final User user) {
        if (user != null) {
            descriptionTextBuilder.append(getString(R.string.transaction_detail_delivery_address, user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getPhone()));
        } else {
            descriptionTextBuilder.append(getString(R.string.transaction_detail_invalid_delivery_address));
        }

        if (deliveryAddress != null) {
            descriptionTextBuilder.append(getString(R.string.transaction_detail_digital_address, deliveryAddress.getLine1(), deliveryAddress.getLine2(), deliveryAddress.getLine3(),
                    deliveryAddress.getLine4(), deliveryAddress.getLine5(), deliveryAddress.getLine6(), deliveryAddress.getPostCode(), deliveryAddress.getCountryCode()));
        } else {
            descriptionTextBuilder.append(getString(R.string.transaction_details_invalid_physical_delivery_address));
        }
    }

    /**
     * Fills payment details view with delivery digital address information.
     *
     * @param descriptionTextBuilder The string builder that contains order details information.
     * @param user                   The consumer.
     */
    private void displayDigitalDeliveryAddress(final StringBuilder descriptionTextBuilder, final User user) {
        if (user != null) {
            descriptionTextBuilder.append(getString(R.string.transaction_detail_delivery_address, user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getPhone()));
        } else {
            descriptionTextBuilder.append(getString(R.string.transaction_details_invalid_digital_delivery_address));
        }
    }
}

