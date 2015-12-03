package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.ACRType;
import com.zapp.core.Address;
import com.zapp.core.AmountDetail;
import com.zapp.core.AmountType;
import com.zapp.core.CheckoutType;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryType;
import com.zapp.core.Merchant;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.User;
import com.zapp.core.ZappModelValidationException;
import com.zapp.library.merchant.model.PaymentRequestBuilder;

import android.net.Uri;
import android.util.Log;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Feature;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.ui.adapter.FeatureDemoAdapter;

/**
 * Is listing all deferred payment journeys.
 *
 * @author nsevciuc
 */
public class DeferredPaymentFragment extends AbstractPaymentFragment {

    /**
     * Tag for logging.
     */
    private static final String TAG = DeferredPaymentFragment.class.getSimpleName();

    /**
     * The tab name displayed in application bar.
     */
    public static final String TAB_NAME = "Deferred";

    @Override
    protected void setupFeatureDemos() {
        if (mFeatures.getFeaturesByRtpType(RTPType.DEFERRED).isEmpty()) {
            setupPayPlusFeatureDemo();
            setupStandardPaymentFeatureDemo();
        }
        mFeaturesAdapter = new FeatureDemoAdapter(R.layout.list_item_feature_demo_deferred, mFeatures.getFeaturesByRtpType(RTPType.DEFERRED), this);
    }

    private void setupPayPlusFeatureDemo() {
        try {
            final String featureTitle = "PayPlus Payment";
            final String featureDescription = "Select 'Pay by Bank app' to begin a PayPlus deferred payment.";

            final CurrencyAmount defrdRTPAgrmtAmount = new CurrencyAmount(1200l, CurrencyAmount.POUNDS); //£12
            final CurrencyAmount defrdRTPMaxAgrdAmount = null; //N/A

            final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
                /* postCode */ "EC1 2EC", Address.UK);
            //logoUrl is null because it is set in Zapp Core already
            final Merchant merchant = new Merchant(mMerchantId, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
                    Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

            final AmountDetail serviceAmountDetail = new AmountDetail(AmountType.FEES, "Service fee", "10.00", /* rate */ null); //£10 service
            final AmountDetail vatAmountDetail = new AmountDetail(AmountType.VATX, "VAT", "20.00", "2.00"); //£2 VAT
            final AmountDetail[] amountDetails = {serviceAmountDetail, vatAmountDetail};

            final Uri.Builder uriBuilder = new Uri.Builder();
            final Uri merchantCallbackUri = uriBuilder
                    .scheme(getString(R.string.app_scheme))
                    .authority("merchant.domain.com")
                    .appendQueryParameter("param1", "value1")
                    .build();

            final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
            paymentRequestBuilder
                    .withRtpType(RTPType.DEFERRED)
                    .withPaymentType(PaymentType.INSTANT_PAYMENT)
                    .withCheckoutType(CheckoutType.QUICK)
                    .withDeliveryType(DeliveryType.ADDRESS)
                    .withMerchant(merchant)
                    .withMerchantCallbackUrl(merchantCallbackUri.toString())
                    .withAcrType(ACRType.FUNDS_CHECK) //do funds checking but no funds guarantee.
                    .withDefrdRTPAgrmtAmount(defrdRTPAgrmtAmount) //£12.00
                    .withDefrdRTPMaxAgrdAmount(defrdRTPMaxAgrdAmount) //N/A
                    .withDefrdAmountDetails(amountDetails);

            final PaymentRequest paymentRequest = paymentRequestBuilder.build();

            final Feature feature = new Feature();
            feature.setTitle(featureTitle);
            feature.setDescription(featureDescription);
            feature.setPaymentRequest(paymentRequest);
            Features.getInstance(mContext).addFeature(feature);

        } catch (Exception e) {
            Log.e(TAG, "Failed to set up PayPlus deferred payment feature demo", e);
        }
    }

    private void setupStandardPaymentFeatureDemo() {
        try {
            final String featureTitle = "Standard Payment";
            final String featureDescription = "Select 'Pay by Bank app' to begin a Standard deferred payment with delivery to address.";

            final CurrencyAmount defrdRTPAgrmtAmount = new CurrencyAmount(1200l, CurrencyAmount.POUNDS); //£12
            final CurrencyAmount defrdRTPMaxAgrdAmount = null; //N/A

            final Address customerAddress = new Address("125 Old Broad St", /*line2*/ null, /*line3*/ "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
            /*postCode*/ "ECN 1AR", Address.UK);
            final User user = new User("John", "Smith", /*middle name*/ null, /*title*/ null, /*email*/ "consumer1@pbba.co.uk", /*phone*/ null);

            final Address merchantStoreAddress = new Address("Zapp Super Store", "2 Puddle Dock", "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
                    /*postCode*/ "EC4V 3DB", Address.UK);

            final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
            /*postCode*/ "EC1 2EC", Address.UK);
            //logoUrl is null because it is set in Zapp Core already
            final Merchant merchant = new Merchant(mMerchantId, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
                    Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

            final Uri.Builder uriBuilder = new Uri.Builder();
            final Uri merchantCallbackUri = uriBuilder
                    .scheme(getString(R.string.app_scheme))
                    .authority("merchant.domain.com")
                    .appendQueryParameter("param1", "value1")
                    .build();

            final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
            paymentRequestBuilder
                    .withRtpType(RTPType.DEFERRED)
                    .withPaymentType(PaymentType.INSTANT_PAYMENT)
                    .withCheckoutType(CheckoutType.NORMAL)
                    .withDeliveryType(DeliveryType.ADDRESS)
                    .withAddress(customerAddress)
                    .withUser(user)
                    .withMerchant(merchant)
                    .withMerchantCallbackUrl(merchantCallbackUri.toString())
                    .withAcrType(ACRType.FUNDS_CHECK) //do funds checking but no funds guarantee.
                    .withDefrdRTPAgrmtAmount(defrdRTPAgrmtAmount) //£12.00
                    .withDefrdRTPMaxAgrdAmount(defrdRTPMaxAgrdAmount); //N/A

            final PaymentRequest paymentRequest = paymentRequestBuilder.build();

            final Feature feature = new Feature();
            feature.setTitle(featureTitle);
            feature.setDescription(featureDescription);
            feature.setPaymentRequest(paymentRequest);
            feature.setCustomerAddress(customerAddress);
            feature.setMerchantStoreAddress(merchantStoreAddress);
            Features.getInstance(mContext).addFeature(feature);

        } catch (ZappModelValidationException e) {
            Log.e(TAG, "Failed to set up standard payment feature demo", e);
        }
    }

}
