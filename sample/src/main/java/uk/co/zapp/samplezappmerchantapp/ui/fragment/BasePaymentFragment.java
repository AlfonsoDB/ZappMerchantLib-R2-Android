package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.Transaction;
import com.zapp.library.merchant.model.SettlementStatus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.text.SimpleDateFormat;

import uk.co.zapp.samplezappmerchantapp.PaymentConfirmedActivity;

/**
 * The base payment confirmation screen.
 *
 * @author nsevciuc
 */
public class BasePaymentFragment extends Fragment {

    /**
     * Key for transaction arguments.
     */
    protected static final String KEY_TRANSACTION = "key_transaction";

    /**
     * Key for settlement status extras.
     */
    protected static final String KEY_SETTLEMENT_STATUS = "key_settlement_status";

    /**
     * Date format.
     */
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    /**
     * The parent activity.
     */
    protected PaymentConfirmedActivity mActivity;

    /**
     * The payment confirmed transaction.
     */
    protected Transaction mTransaction;

    /**
     * The payment transaction settlement status.
     */
    protected SettlementStatus mSettlementStatus;


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mActivity = (PaymentConfirmedActivity) context;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransaction = (Transaction) getArguments().getSerializable(KEY_TRANSACTION);
        mSettlementStatus = (SettlementStatus) getArguments().getSerializable(KEY_SETTLEMENT_STATUS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
