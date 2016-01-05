package uk.co.zapp.samplezappmerchantapp;

import com.zapp.core.PaymentType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionStatus;
import com.zapp.library.merchant.model.SettlementStatus;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import uk.co.zapp.samplezappmerchantapp.ui.fragment.PaymentConfirmedFragment;
import uk.co.zapp.samplezappmerchantapp.ui.fragment.PaymentDeclinedFragment;

/**
 * The payment confirmation screen.
 */
public class PaymentConfirmedActivity extends AbstractActivity {

    /**
     * Key for transaction extras.
     */
    private static final String KEY_TRANSACTION = "key_transaction";

    /**
     * Key for settlement status extras.
     */
    private static final String KEY_SETTLEMENT_STATUS = "key_settlement_status";

    /**
     * The payment request transaction.
     */
    private Transaction mTransaction;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmed);
        setActionBar();

        mTransaction = (Transaction) getIntent().getSerializableExtra(KEY_TRANSACTION);

        if (mTransaction != null) {

            final SettlementStatus settlementStatus = (SettlementStatus) getIntent().getSerializableExtra(KEY_SETTLEMENT_STATUS);
            final TransactionStatus transactionStatus = mTransaction.getTransactionStatus();

            //settlement status arrives from Settlement Status Enquiry, transaction status arrives from Retrieve Payment Notification endpoint
            //one of them should be present at this point
            if (settlementStatus == null && transactionStatus == null) {
                throw new IllegalStateException("Cannot determine status of the transaction");
            }
            //if settlement status is set then we act on it, otherwise we act on transaction status
            final boolean isTransactionAuthorised = settlementStatus == SettlementStatus.AUTHORISED || transactionStatus == TransactionStatus.AUTHORIZED;

            switchFragment(isTransactionAuthorised ? PaymentConfirmedFragment.newInstance(mTransaction, settlementStatus)
                    : PaymentDeclinedFragment.newInstance(mTransaction, settlementStatus), /*isAddedToBackStack*/ false);
        } else {
            throw new IllegalArgumentException(KEY_TRANSACTION + " intent extra is required");
        }

    }

    @Override
    public void onBackPressed() {
        final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount == 0) {
            if (mTransaction != null && mTransaction.getPaymentRequest().getPaymentType() == PaymentType.SMB) {
                final Intent intent = new Intent(PaymentConfirmedActivity.this, SMBScreenActivity.class);
                startActivity(intent);
            } else {
                final Intent intent = new Intent(this, FeatureDemoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A factory method responsible to start a new PaymentConfirmedActivity.
     *
     * @param context          The package context.
     * @param transaction      The payment transaction.
     * @param settlementStatus The transactions settlement status.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public static void startActivity(final MainActivity context, final Transaction transaction, final SettlementStatus settlementStatus) {
        final Intent confirmationIntent = new Intent(context, PaymentConfirmedActivity.class);
        confirmationIntent.putExtra(PaymentConfirmedActivity.KEY_TRANSACTION, transaction);
        confirmationIntent.putExtra(PaymentConfirmedActivity.KEY_SETTLEMENT_STATUS, settlementStatus);
        context.startActivity(confirmationIntent);
    }
}
