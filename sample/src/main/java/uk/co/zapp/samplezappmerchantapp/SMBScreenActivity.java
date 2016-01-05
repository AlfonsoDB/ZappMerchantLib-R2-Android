package uk.co.zapp.samplezappmerchantapp;

import com.zapp.core.Transaction;
import com.zapp.library.merchant.ui.IPBBAPopupCallback;
import com.zapp.library.merchant.ui.fragment.PBBAPopupTimerFragment;
import com.zapp.library.merchant.view.TintedImageView;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * The payment confirmation screen.
 */
public class SMBScreenActivity extends AbstractActivity implements IPBBAPopupCallback {

    /**
     * Log tag.
     */
    private static final String TAG = SMBScreenActivity.class.getSimpleName();

    /**
     * Key for transaction extras.
     */
    public static final String KEY_TRANSACTION = "key_transaction";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smb_screen);
        setActionBar();

        final Transaction transaction = (Transaction) getIntent().getSerializableExtra(KEY_TRANSACTION);
        if (transaction != null) {
            final char[] code = transaction.getBrn().toCharArray();
            ((TextView) findViewById(com.zapp.library.merchant.R.id.code_value_1)).setText(String.valueOf(code[0]));
            ((TextView) findViewById(com.zapp.library.merchant.R.id.code_value_2)).setText(String.valueOf(code[1]));
            ((TextView) findViewById(com.zapp.library.merchant.R.id.code_value_3)).setText(String.valueOf(code[2]));
            ((TextView) findViewById(com.zapp.library.merchant.R.id.code_value_4)).setText(String.valueOf(code[3]));
            ((TextView) findViewById(com.zapp.library.merchant.R.id.code_value_5)).setText(String.valueOf(code[4]));
            ((TextView) findViewById(com.zapp.library.merchant.R.id.code_value_6)).setText(String.valueOf(code[5]));

            final PBBAPopupTimerFragment timerFragment = PBBAPopupTimerFragment.newInstance(transaction);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(timerFragment, PBBAPopupTimerFragment.class.getSimpleName())
                    .commit();

            final TintedImageView progressImageView = (TintedImageView) findViewById(R.id.progress);
            final Animatable animation = (Animatable) progressImageView.getDrawable();
            animation.start();
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

    @Override
    public void onPaymentRetrievalExpired() {
        Log.d(TAG, "onPaymentRetrievalExpired");
        finish();
    }

    @Override
    public void onPaymentConfirmationExpired() {
        Log.d(TAG, "onPaymentConfirmationExpired");
        finish();
    }

    @Override
    public void onPaymentDeclined() {
        Log.d(TAG, "onPaymentDeclined");
        finish();
    }

    @Override
    public void onError(final com.zapp.library.merchant.exception.Error error) {
        Log.e(TAG, "onError: " + error);
        finish();
    }

    @Override
    public void onDisplayCode() {
        //this callback should not happen by the flow
        Log.e(TAG, "onDisplayCode");
        finish();
    }

    @Override
    public void onPayByBankApp() {
        //this callback should not happen by the flow
        Log.e(TAG, "onPayByBankApp");
        finish();
    }

    @Override
    public void onNoBankAppAvailable() {
        //this callback should not happen by the flow
        Log.d(TAG, "onNoBankAppAvailable");
        finish();
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
        finish();
    }
}
