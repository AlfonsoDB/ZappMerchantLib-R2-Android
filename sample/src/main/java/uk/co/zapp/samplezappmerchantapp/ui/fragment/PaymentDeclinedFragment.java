package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.CurrencyAmount;
import com.zapp.core.Merchant;
import com.zapp.core.PaymentRequest;
import com.zapp.core.RTPType;
import com.zapp.core.Transaction;
import com.zapp.library.merchant.model.SettlementStatus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import uk.co.zapp.samplezappmerchantapp.R;

/**
 * The payment confirmation declined screen.
 */
public class PaymentDeclinedFragment extends BasePaymentFragment {

    private TextView mOrderNumber;

    private TextView mOrderDate;

    private TextView mOrderTotal;

    private TextView mMaxAgreedAmount;

    /**
     * Creates a new instance of {@link PaymentDeclinedFragment}
     *
     * @param transaction      The payment transaction argument.
     * @param settlementStatus The payment transaction settlement status.
     * @return The fragment instance.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public static Fragment newInstance(final Transaction transaction, final SettlementStatus settlementStatus) {
        final Fragment fragment = new PaymentDeclinedFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_TRANSACTION, transaction);
        args.putSerializable(KEY_SETTLEMENT_STATUS, settlementStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_payment_declined, container, false);
        mOrderNumber = (TextView) view.findViewById(R.id.order_number);
        mOrderDate = (TextView) view.findViewById(R.id.order_date);
        mOrderTotal = (TextView) view.findViewById(R.id.order_total);
        mMaxAgreedAmount = (TextView) view.findViewById(R.id.max_agreed_amount);
        view.findViewById(R.id.additional_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mActivity.switchFragment(PaymentDetailsFragment.newInstance(mTransaction, mSettlementStatus));
            }
        });

        mOrderNumber.setText(mTransaction.getTransactionId().getAptrId());
        mOrderDate.setText(DATE_FORMAT.format(new Date()));

        final PaymentRequest paymentRequest = mTransaction.getPaymentRequest();
        final RTPType rtpType = paymentRequest.getRtpType();

        if (rtpType == RTPType.IMMEDIATE) {
            mOrderTotal.setText(paymentRequest.getAmount().toString());
        } else {
            mOrderTotal.setText(paymentRequest.getDefrdRTPAgrmtAmount().toString());
            final Merchant merchant = paymentRequest.getMerchant();
            final CurrencyAmount defrdRTPMaxAgrdAmount = paymentRequest.getDefrdRTPMaxAgrdAmount();
            if (defrdRTPMaxAgrdAmount != null) {
                final String htmlMaxAmount = getString(R.string.transaction_declined_agreed_max_amount, merchant.getName(), defrdRTPMaxAgrdAmount.toString());
                mMaxAgreedAmount.setText(Html.fromHtml(htmlMaxAmount));
            }
        }

        return view;
    }
}
