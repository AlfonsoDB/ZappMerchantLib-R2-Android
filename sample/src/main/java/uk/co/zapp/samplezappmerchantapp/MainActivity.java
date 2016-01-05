package uk.co.zapp.samplezappmerchantapp;

import com.squareup.seismic.ShakeDetector;
import com.zapp.core.RTPType;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionStatus;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.ProcessResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;
import com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory;
import com.zapp.library.merchant.service.provider.IMerchantService;

import android.content.Intent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;

import uk.co.zapp.samplezappmerchantapp.configuration.AppConfigurationUtils;
import uk.co.zapp.samplezappmerchantapp.configuration.Feature;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.network.delegate.MerchantNetworkServiceDelegateImpl;
import uk.co.zapp.samplezappmerchantapp.ui.MerchantUIDelegateImpl;
import uk.co.zapp.samplezappmerchantapp.ui.adapter.PaymentViewPagerAdapter;
import uk.co.zapp.samplezappmerchantapp.ui.fragment.DeferredPaymentFragment;
import uk.co.zapp.samplezappmerchantapp.ui.fragment.ImmediatePaymentFragment;
import uk.co.zapp.samplezappmerchantapp.ui.fragment.dialog.GatewayPickerDialogFragment;
import uk.co.zapp.samplezappmerchantapp.util.UserPrefs;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {

    /**
     * Constant for 'show payment confirmation screen' intent action.
     */
    public static final String SHOW_PAYMENT_CONFIRMATION_ACTION = "show_payment_confirmation_screen";

    /**
     * Constant for 'payment details key' intent field key.
     */
    public static final String KEY_PAYMENT_DETAILS = "payment_details_key";

    /**
     * Constant for 'payment error key' intent field key.
     */
    public static final String KEY_PAYMENT_ERROR = "payment_error_key";

    /**
     * Tag for logging.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * The delay before showing the version info snackbar.
     */
    private static final int VERSION_INFO_SNACKBAR_SHOW_DELAY_MS = 1000;

    /**
     * Shake detector to display gateway picker on shake.
     */
    private ShakeDetector mShakeDetector;

    /**
     * Handler on the UI thread
     */
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    /**
     * The root layout.
     */
    private CoordinatorLayout mRootLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootLayout = (CoordinatorLayout) findViewById(R.id.root_layout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.feature_demo_view_pager);
        final PaymentViewPagerAdapter adapter = new PaymentViewPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new ImmediatePaymentFragment(), ImmediatePaymentFragment.TAB_NAME);
        adapter.addItem(new DeferredPaymentFragment(), DeferredPaymentFragment.TAB_NAME);
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mShakeDetector = new ShakeDetector(this);

        final String versionInfo = String.format("%s %s", getString(getApplicationInfo().labelRes), BuildConfig.VERSION_NAME);
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mRootLayout, versionInfo, Snackbar.LENGTH_LONG).show();
            }
        }, VERSION_INFO_SNACKBAR_SHOW_DELAY_MS);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            final Uri uri = intent.getData();
            if (SHOW_PAYMENT_CONFIRMATION_ACTION.equals(intent.getAction())) {
                //this branch hits the Merchant UI delegate showing confirmation screen (from Merchant App)
                final Transaction transaction = (Transaction) intent.getSerializableExtra(KEY_PAYMENT_DETAILS);
                final com.zapp.library.merchant.exception.Error paymentError = (com.zapp.library.merchant.exception.Error) intent.getSerializableExtra(KEY_PAYMENT_ERROR);
                if (transaction != null) {
                    final TransactionStatus transactionStatus = transaction.getTransactionStatus();
                    if (transactionStatus == TransactionStatus.AUTHORIZED || transactionStatus == TransactionStatus.DECLINED) {
                        showPaymentConfirmationScreen(transaction, /*settlementStatus*/ null);
                    } else {
                        Toast.makeText(this, "Transaction Status: " + transaction.getTransactionStatus(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, paymentError.getErrorMessage(this), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (uri != null && uri.isHierarchical()) {
                    //this branch is hit by the Bank App (re)opening the Merchant App on Payment Finish or Cancel (using intent)
                    final String aptrId = uri.getQueryParameter("aptrId");

                    final Transaction transaction = UserPrefs.readTransactionObject(this);
                    transaction.getTransactionId().setAptrId(aptrId);

                    final IMerchantService merchantServiceProvider = ZappMerchantServiceFactory.createMerchantService(new MerchantNetworkServiceDelegateImpl(this),
                            new MerchantUIDelegateImpl(this));
                    if (transaction.getPaymentRequest().getRtpType() == RTPType.DEFERRED) {
                        merchantServiceProvider.notifyMerchantPayment(transaction, new ProcessResponseListener<Transaction>() {
                            @Override
                            public void onStart() {
                                Toast.makeText(MainActivity.this, "Checking payment status", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(final com.zapp.library.merchant.exception.Error error) {
                                Toast.makeText(MainActivity.this, error.getErrorMessage(MainActivity.this), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(final ResponseWrapper<Transaction> response) {
                                final Transaction responseTransaction = response.getResponse();
                                if (responseTransaction.getTransactionStatus() != TransactionStatus.AUTHORIZED) {
                                    //in case of AUTHORIZED, the merchant service calls UI delegate .showPaymentConfirmationScreen so there is no need to display it twice
                                    showPaymentConfirmationScreen(responseTransaction, /*settlementStatus*/ null);
                                }
                            }
                        });
                    } else {
                        final String merchantId = transaction.getPaymentRequest().getMerchant().getIdentifier();
                        final String settlementRetrievalId = transaction.getSettlementRetrievalId();
                        merchantServiceProvider.getSettlementStatus(merchantId, settlementRetrievalId, new ProcessResponseListener<SettlementStatus>() {
                            @Override
                            public void onStart() {
                                Toast.makeText(MainActivity.this, "Checking payment settlement status", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(final com.zapp.library.merchant.exception.Error error) {
                                Toast.makeText(MainActivity.this, error.getErrorMessage(MainActivity.this), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(final ResponseWrapper<SettlementStatus> response) {
                                final SettlementStatus settlementStatus = response.getResponse();
                                showPaymentConfirmationScreen(transaction, settlementStatus);
                            }
                        });
                    }

                } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                    Toast.makeText(MainActivity.this, "Callback error. Failed to process callback data from bank app.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showPaymentConfirmationScreen(final Transaction transaction, final SettlementStatus settlementStatus) {
        PaymentConfirmedActivity.startActivity(this, transaction, settlementStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeDetector.start((SensorManager) getSystemService(SENSOR_SERVICE));
    }

    @Override
    public void hearShake() {
        final GatewayPickerDialogFragment fragment = (GatewayPickerDialogFragment) getSupportFragmentManager().findFragmentByTag(GatewayPickerDialogFragment.TAG);
        if (fragment == null && !isFinishing()) {
            final GatewayPickerDialogFragment dialogFragment = new GatewayPickerDialogFragment();
            dialogFragment.setCallback(new GatewayPickerDialogFragment.IGatewayPickerDialogCallback() {
                @Override
                public void onGatewayPicked(@NonNull final String gatewayInfo) {
                    Snackbar.make(mRootLayout, getString(R.string.settings_gateway_updated, gatewayInfo), Snackbar.LENGTH_LONG).show();

                    final List<Feature> features = Features.getInstance(getApplicationContext()).getFeatures();
                    final String merchantId = AppConfigurationUtils.getInstance(getApplicationContext()).getClientId();

                    for (final Feature feature : features) {
                        feature.getPaymentRequest().getMerchant().setIdentifier(merchantId);
                    }
                }
            });
            dialogFragment.show(getSupportFragmentManager(), GatewayPickerDialogFragment.TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeDetector.stop();
    }
}
