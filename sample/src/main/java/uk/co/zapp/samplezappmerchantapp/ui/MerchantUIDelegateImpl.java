package uk.co.zapp.samplezappmerchantapp.ui;

import com.zapp.core.Transaction;
import com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate;

import android.content.Context;
import android.content.Intent;

import uk.co.zapp.samplezappmerchantapp.MainActivity;
import uk.co.zapp.samplezappmerchantapp.SMBScreenActivity;

/**
 * UI delegate implementation.
 */
public class MerchantUIDelegateImpl extends ZappMerchantUIDelegate {

    /**
     * Create new instance.
     *
     * @param context The context to use.
     */
    public MerchantUIDelegateImpl(final Context context) {
        super(context);
    }

    @Override
    public void showPaymentConfirmationScreen(final Transaction transaction, final com.zapp.library.merchant.exception.Error error) {
        final Intent showPaymentConfirmIntent = new Intent(MainActivity.SHOW_PAYMENT_CONFIRMATION_ACTION, null, mContext, MainActivity.class);
        showPaymentConfirmIntent.putExtra(MainActivity.KEY_PAYMENT_DETAILS, transaction);
        showPaymentConfirmIntent.putExtra(MainActivity.KEY_PAYMENT_ERROR, error);
        mContext.startActivity(showPaymentConfirmIntent);
    }

    @Override
    public void showSMBScreen(final Transaction transaction) {
        final Intent showSMBScreenIntent = new Intent(mContext, SMBScreenActivity.class);
        showSMBScreenIntent.putExtra(SMBScreenActivity.KEY_TRANSACTION, transaction);
        mContext.startActivity(showSMBScreenIntent);
    }
}
