package uk.co.zapp.samplezappmerchantapp;

import com.zapp.core.RTPType;

import android.os.Bundle;

import uk.co.zapp.samplezappmerchantapp.configuration.Feature;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.ui.fragment.FeatureDemoDeferredFragment;
import uk.co.zapp.samplezappmerchantapp.ui.fragment.FeatureDemoImmediateFragment;

/**
 * Feature demo activity.
 *
 * @author msagi
 */
public class FeatureDemoActivity extends AbstractActivity {

    /**
     * Key for intent extra 'feature id'.
     */
    public static final String KEY_FEATURE_ID = "key.featureId";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_demo);
        setActionBar();

        if (savedInstanceState == null) {
            final int mFeatureId = getIntent().getIntExtra(KEY_FEATURE_ID, -1);
            final Feature feature = Features.getInstance(getApplicationContext()).getFeatureById(mFeatureId);
            if (feature == null) {
                return;
            }
            if (feature.getPaymentRequest().getRtpType() == RTPType.IMMEDIATE) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.feature_demo_container, FeatureDemoImmediateFragment.newInstance(feature), FeatureDemoImmediateFragment.TAG).commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.feature_demo_container, FeatureDemoDeferredFragment.newInstance(feature), FeatureDemoDeferredFragment.TAG).commit();
            }
        }
    }
}
