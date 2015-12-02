package com.zapp.library.merchant.service.delegate;

import com.zapp.core.PaymentRequest;
import com.zapp.core.Transaction;
import com.zapp.library.merchant.ui.PBBAPopupActivity;

import android.content.Context;
import android.content.Intent;

/**
 * The implementation of {@link IZappMerchantUIDelegate}. This class overrides only two methods that should not be overridden by the user.
 * However it is an abstract class that forces the user to override the third method {@link #showPaymentConfirmationScreen} which should be specific
 * to merchant.
 *
 * @author Vasile Chelban on 7/1/2014.
 */
public abstract class ZappMerchantUIDelegate implements IZappMerchantUIDelegate {

    /**
     * The context.
     */
    protected final Context mContext;

    /**
     * Create new instance.
     *
     * @param context The context to use.
     */
    protected ZappMerchantUIDelegate(final Context context) {
        mContext = context;
    }

    @Override
    public final void showPopupDialog(final Transaction transaction, final PaymentRequest paymentRequest, final com.zapp.library.merchant.exception.Error error) {
        final Intent dialogIntent = new Intent(mContext, PBBAPopupActivity.class);
        dialogIntent.putExtra(PBBAPopupActivity.KEY_PAYMENT_OBJECT, transaction);
        dialogIntent.putExtra(PBBAPopupActivity.KEY_PAYMENT_REQUEST_OBJECT, paymentRequest);
        dialogIntent.putExtra(PBBAPopupActivity.KEY_PAYMENT_ERROR, error);
        mContext.startActivity(dialogIntent);
    }

    @Override
    public void showSMBScreen(final Transaction transaction) {
        //leave it empty to not force the merchant apps to override this method
    }
}
