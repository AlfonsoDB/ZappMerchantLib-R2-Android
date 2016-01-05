package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.Address;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryAddress;
import com.zapp.core.DeliveryType;
import com.zapp.core.Merchant;
import com.zapp.core.PaymentAuth;
import com.zapp.core.PaymentRequest;
import com.zapp.core.RTPType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.core.User;
import com.zapp.library.merchant.model.SettlementStatus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import uk.co.zapp.samplezappmerchantapp.R;

/**
 * The payment confirmation screen.
 *
 * @author nsevciuc
 */
public class PaymentConfirmedFragment extends BasePaymentFragment {

    /**
     * The view for displaying payment details.
     */
    private TextView mPaymentDetailsTextView;

    /**
     * The view for displaying address.
     */
    private TextView mPaymentAddressTextView;

    /**
     * The view link for opening payment details fragment.
     */
    private TextView mPaymentDescriptionTextView;

    /**
     * Creates a new instance of {@link PaymentConfirmedFragment}
     *
     * @param transaction      The payment transaction argument.
     * @param settlementStatus The payment transaction settlement status.
     * @return The fragment instance.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public static Fragment newInstance(final Transaction transaction, final SettlementStatus settlementStatus) {
        final Fragment fragment = new PaymentConfirmedFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_TRANSACTION, transaction);
        args.putSerializable(KEY_SETTLEMENT_STATUS, settlementStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_payment_confirmed, container, false);
        mPaymentAddressTextView = (TextView) view.findViewById(R.id.payment_confirmation_address);
        mPaymentDetailsTextView = (TextView) view.findViewById(R.id.payment_confirmation_details);
        mPaymentDescriptionTextView = (TextView) view.findViewById(R.id.payment_confirmation_description);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        if (mTransaction == null) {
            mPaymentDescriptionTextView.setText(R.string.error_invalid_transaction_details);
        } else {
            displayOrderInfo();
            displayShippingAddress();
            setupOrderDetailsView();
        }
    }

    /**
     * Fills payment details view with general order information.
     */
    private void displayOrderInfo() {
        final StringBuilder descriptionTextBuilder = new StringBuilder();
        final PaymentRequest paymentRequest = mTransaction.getPaymentRequest();
        final RTPType rtpType = paymentRequest.getRtpType();
        final TransactionId transactionId = mTransaction.getTransactionId();

        final String aptrId = transactionId == null ? getString(R.string.transaction_details_not_available) : transactionId.getAptrId();
        descriptionTextBuilder.append(getString(R.string.transaction_order_number, aptrId));
        descriptionTextBuilder.append(getString(R.string.transaction_confirmation_email_text));

        final String orderDate = DATE_FORMAT.format(new Date());
        descriptionTextBuilder.append(getString(R.string.transaction_order_date, orderDate));

        switch (rtpType) {
            case IMMEDIATE:
                descriptionTextBuilder.append(getString(R.string.transaction_order_total, paymentRequest.getAmount().toString()));
                break;
            case DEFERRED:
                descriptionTextBuilder.append(getString(R.string.transaction_order_total, paymentRequest.getDefrdRTPAgrmtAmount().toString()));
                final Merchant merchant = paymentRequest.getMerchant();
                final CurrencyAmount defrdRTPMaxAgrdAmount = paymentRequest.getDefrdRTPMaxAgrdAmount();
                if (defrdRTPMaxAgrdAmount != null) {
                    descriptionTextBuilder.append(getString(R.string.transaction_agreed_max_amount, merchant.getName(), defrdRTPMaxAgrdAmount.toString()));
                }
                break;
            default:
                descriptionTextBuilder.append(getString(R.string.transaction_order_total, getString(R.string.transaction_details_not_available)));
                break;
        }

        mPaymentDescriptionTextView.setText(Html.fromHtml(descriptionTextBuilder.toString()));

    }

    /**
     * Fills payment address view.
     */
    private void displayShippingAddress() {
        final StringBuilder descriptionTextBuilder = new StringBuilder();
        final PaymentAuth paymentAuth = mTransaction.getPaymentAuth();
        final PaymentRequest paymentRequest = mTransaction.getPaymentRequest();
        final DeliveryType deliveryType = paymentRequest.getDeliveryType();
        final DeliveryAddress deliveryAddress = paymentAuth == null ? null : paymentAuth.getDeliveryAddress();
        switch (deliveryType) {
            case ADDRESS:
            case COLLECT_IN_STORE:
            case SERVICE:
                if (paymentRequest.getRtpType() == RTPType.DEFERRED) {
                    if (deliveryAddress != null) {
                        final User user = deliveryAddress.getAddressee();
                        if (user != null) {
                            final String[] addressArray = {getCustomerName(user), user.getEmail(), user.getPhone(), deliveryAddress.getLine1(),
                                    deliveryAddress.getLine2(), deliveryAddress.getLine3(), deliveryAddress.getLine4(), deliveryAddress.getLine5(),
                                    deliveryAddress.getLine6(), deliveryAddress.getPostCode(), deliveryAddress.getCountryCode()};
                            displayAddresseeInfo(descriptionTextBuilder, addressArray);
                        }
                    }
                } else {
                    //TODO as PaymentAuth does not contain the SettlementStatus, it cannot be used for payment authorisation for immediate payments,
                    //TODO retrieve the shipping address from PaymentRequest.
                    final User user = paymentRequest.getUser();
                    final Address address = paymentRequest.getAddress();
                    if (user != null) {
                        final String[] addressArray = {getCustomerName(user), user.getEmail(), user.getPhone(), address.getLine1(), address.getLine2(),
                                address.getLine3(), address.getLine4(), address.getLine5(), address.getLine6(), address.getPostCode(), address.getCountryCode()};
                        displayAddresseeInfo(descriptionTextBuilder, addressArray);
                    }
                }
                break;
            case DIGITAL:
                final User user = paymentAuth != null ? paymentAuth.getUser() : paymentRequest.getUser();
                if (user != null) {
                    final String[] addressArray = {getCustomerName(user), user.getEmail(), user.getPhone()};
                    displayAddresseeInfo(descriptionTextBuilder, addressArray);
                }
                break;
            default:
                break;
        }

        if (descriptionTextBuilder.length() > 0) {
            mPaymentAddressTextView.setText(Html.fromHtml(descriptionTextBuilder.toString()));
            mPaymentAddressTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Concatenates firstName and lastName.
     *
     * @param user The consumer.
     * @return The customers firstName and lastName.
     */
    @NonNull
    private String getCustomerName(final User user) {
        final StringBuilder customerName = new StringBuilder();
        if (!TextUtils.isEmpty(user.getFirstName())) {
            customerName.append(user.getFirstName());
        }

        if (!TextUtils.isEmpty(user.getLastName())) {
            customerName.append(" ").append(user.getLastName());
        }
        return customerName.toString();
    }

    /**
     * Fills addressee information.
     *
     * @param descriptionTextBuilder The string builder that contains order details information.
     * @param addressArray           The string array that contains the addressee information to be displayed.
     */
    private void displayAddresseeInfo(final StringBuilder descriptionTextBuilder, final String[] addressArray) {
        descriptionTextBuilder.append(getString(R.string.transaction_delivery_address_header));

        for (final String s : addressArray) {
            if (!TextUtils.isEmpty(s)) {
                descriptionTextBuilder.append(getString(R.string.transaction_physical_address, s));
            }
        }
    }

    /**
     * Sets the view link for opening payment details fragment.
     */
    private void setupOrderDetailsView() {
        final ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(final View view) {
                if (mActivity != null) {
                    mActivity.switchFragment(PaymentDetailsFragment.newInstance(mTransaction, mSettlementStatus));
                }
            }
        };
        final SpannableString paymentDetailsString = new SpannableString(getString(R.string.transaction_technical_details_text_link));
        paymentDetailsString.setSpan(clickableSpan, 0, paymentDetailsString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mPaymentDetailsTextView.setText(paymentDetailsString);
        mPaymentDetailsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mPaymentDetailsTextView.setHighlightColor(Color.TRANSPARENT);
        mPaymentDetailsTextView.setVisibility(View.VISIBLE);
    }

}
