package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import com.zapp.core.Address;
import com.zapp.core.BillDetails;
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

import java.util.Date;

import uk.co.zapp.samplezappmerchantapp.IListItemClickListener;
import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Feature;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.ui.adapter.FeatureDemoAdapter;

/**
 * Is listing all immediate payment journeys.
 *
 * @author nsevciuc
 */
public class ImmediatePaymentFragment extends AbstractPaymentFragment implements IListItemClickListener {

    /**
     * Tag for logging.
     */
    private static final String TAG = ImmediatePaymentFragment.class.getSimpleName();

    /**
     * The tab name displayed in application bar.
     */
    public static final String TAB_NAME = "Immediate";

    /**
     * Set up the feature demos one by one.
     */
    protected void setupFeatureDemos() {
        if (mFeatures.getFeaturesByRtpType(RTPType.IMMEDIATE).isEmpty()) {
            setupPayPlusFeatureDemo();
            setupBillPaymentFeatureDemo();
            setupSMBPaymentFeatureDemo();
            setupStandardPaymentFeatureDemo("Standard - Physical Delivery", "Select 'Pay by Bank app' to begin a Standard payment with delivery to address.",
                    DeliveryType.ADDRESS);
            setupStandardPaymentFeatureDemo("Standard - Click and Collect", "Select 'Pay by Bank app' to begin a Standard payment with collect in store.",
                    DeliveryType.COLLECT_IN_STORE);
            setupStandardPaymentFeatureDemo("Standard - Digital Delivery", "Select 'Pay by Bank app' to begin a Standard payment with digital delivery.",
                    DeliveryType.DIGITAL);
            setupStandardPaymentFeatureDemo("Standard - Face to Face", "Select 'Pay by Bank app' to begin a Standard, face to face payment.",
                    DeliveryType.FACE_2_FACE);
            setupStandardPaymentFeatureDemo("Standard - Service", "Select 'Pay by Bank app' to begin a Standard, service payment.", DeliveryType.SERVICE);
        }
        mFeaturesAdapter = new FeatureDemoAdapter(R.layout.list_item_feature_demo_immediate, mFeatures.getFeaturesByRtpType(RTPType.IMMEDIATE), this);
    }

    private void setupPayPlusFeatureDemo() {
        try {
            final String featureTitle = "PayPlus Payment";
            final String featureDescription = "Select 'Pay by Bank app' to begin a PayPlus immediate payment.";
            final CurrencyAmount amount = new CurrencyAmount(1000l, CurrencyAmount.POUNDS);

            final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null, /*line5*/ null, /*line6*/ null,
                /* postCode */ null, Address.UK);
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
                    .withRtpType(RTPType.IMMEDIATE)
                    .withPaymentType(PaymentType.INSTANT_PAYMENT)
                    .withCheckoutType(CheckoutType.QUICK)
                    .withDeliveryType(DeliveryType.ADDRESS)
                    .withAmount(amount)
                    .withMerchant(merchant)
                    .withMerchantCallbackUrl(merchantCallbackUri.toString());

            final PaymentRequest paymentRequest = paymentRequestBuilder.build();

            final Feature feature = new Feature();
            feature.setTitle(featureTitle);
            feature.setDescription(featureDescription);
            feature.setPaymentRequest(paymentRequest);
            Features.getInstance(mContext).addFeature(feature);

        } catch (Exception e) {
            Log.e(TAG, "Failed to set up PayPlus immediate payment feature demo", e);
        }
    }

    private void setupBillPaymentFeatureDemo() {
        try {
            final String featureTitle = "Bill Pay Payment";
            final CurrencyAmount amount = new CurrencyAmount(1400l, CurrencyAmount.POUNDS);
            final String featureDescription = "Select 'Pay by Bank app' to begin a Bill Pay payment.";

            final Date periodFrom = DATE_FORMATTER.parse("2015-08-01");
            final Date periodTo = DATE_FORMATTER.parse("2015-08-30");
            final BillDetails billDetails = new BillDetails("8500 222 66138", "Y6557802", periodFrom, periodTo);

            final Address customerAddress = new Address("10 Downing Street", /*line2*/ null, /*line3*/ "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
            /*postCode*/ "SW1A 2AA", Address.UK);
            final User user = new User("John", "Smith", /*middle name*/ null, /*title*/ null, /*email*/ null, /*phone*/ null);

            final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null, /*line5*/ null, /*line6*/ null,
            /*postCode*/ null, Address.UK);
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
                    .withRtpType(RTPType.IMMEDIATE)
                    .withPaymentType(PaymentType.BILL_PAY)
                    .withCheckoutType(CheckoutType.NORMAL)
                    .withDeliveryType(DeliveryType.ADDRESS)
                    .withAmount(amount)
                    .withAddress(customerAddress)
                    .withUser(user)
                    .withMerchant(merchant)
                    .withBillDetails(billDetails)
                    .withMerchantCallbackUrl(merchantCallbackUri.toString());

            final PaymentRequest paymentRequest = paymentRequestBuilder.build();

            final Feature feature = new Feature();
            feature.setTitle(featureTitle);
            feature.setDescription(featureDescription);
            feature.setPaymentRequest(paymentRequest);
            Features.getInstance(mContext).addFeature(feature);

        } catch (Exception e) {
            Log.e(TAG, "Failed to set up bill payment feature demo", e);
        }
    }

    private void setupStandardPaymentFeatureDemo(final String featureTitle, final String featureDescription, final DeliveryType deliveryType) {
        try {
            final CurrencyAmount amount = new CurrencyAmount(1200l, CurrencyAmount.POUNDS);

            final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null, /*line5*/ null,
             /*line6*/ null, /*postCode*/ null, Address.UK);

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
                    .withRtpType(RTPType.IMMEDIATE)
                    .withPaymentType(PaymentType.INSTANT_PAYMENT)
                    .withCheckoutType(CheckoutType.NORMAL)
                    .withDeliveryType(deliveryType)
                    .withAmount(amount)
                    .withMerchant(merchant)
                    .withMerchantCallbackUrl(merchantCallbackUri.toString());

            final User user;
            switch (deliveryType) {
                case ADDRESS:
                    final Address customerAddress = new Address("10 Downing Street", /* line2 */ null, "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
                    /*postCode*/ "SW1A 2AA", Address.UK);
                    user = new User("John", "Smith", /*middle name*/ null, /*title*/ null, /*email*/ null, /*phone*/ null);
                    paymentRequestBuilder.withAddress(customerAddress).withUser(user);
                    break;
                case COLLECT_IN_STORE:
                    final Address merchantStoreAddress = new Address("Zapp Super Store", "2 Puddle Dock", "London", /*line4*/ null, /*line5*/ null, /*line6*/ null,
                    /*postCode*/ "EC4V 3DB", Address.UK);
                    user = new User("John", "Smith", /*middle name*/ null, /*title*/ null, /*email*/ null, /*phone*/ null);
                    paymentRequestBuilder.withAddress(merchantStoreAddress).withUser(user);
                    break;
                case DIGITAL:
                    user = new User(/*firstName*/ null, /*lastName*/ null, /*middle name*/ null, /*title*/ null, "consumer@zapp.co.uk", /*phone*/ null);
                    paymentRequestBuilder.withUser(user);
                    break;
                default:
                    //no specific information for FACE_2_FACE and SERVICE delivery type is required
                    break;
            }

            final PaymentRequest paymentRequest = paymentRequestBuilder.build();

            final Feature feature = new Feature();
            feature.setTitle(featureTitle);
            feature.setDescription(featureDescription);
            feature.setPaymentRequest(paymentRequest);
            Features.getInstance(mContext).addFeature(feature);

        } catch (ZappModelValidationException e) {
            Log.e(TAG, "Failed to set up quick payment feature demo", e);
        }
    }

    private void setupSMBPaymentFeatureDemo() {
        try {
            final String featureTitle = "SMB Payment";
            final CurrencyAmount amount = new CurrencyAmount(1600l, CurrencyAmount.POUNDS);
            final String featureDescription = "Select 'Pay by Bank app' to begin a Small and Micro Business payment.";

            final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null, /*line5*/ null, /*line6*/ null,
            /*postCode*/ null, Address.UK);
            //logoUrl is null because it is set in Zapp Core already
            final Merchant merchant = new Merchant(mMerchantId, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
                    Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

            final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
            paymentRequestBuilder
                    .withRtpType(RTPType.IMMEDIATE)
                    .withPaymentType(PaymentType.SMB)
                    .withCheckoutType(CheckoutType.NORMAL)
                    .withDeliveryType(DeliveryType.ADDRESS)
                    .withAmount(amount)
                    .withMerchant(merchant);

            final PaymentRequest paymentRequest = paymentRequestBuilder.build();

            final Feature feature = new Feature();
            feature.setTitle(featureTitle);
            feature.setDescription(featureDescription);
            feature.setPaymentRequest(paymentRequest);
            Features.getInstance(mContext).addFeature(feature);

        } catch (ZappModelValidationException e) {
            Log.e(TAG, "Failed to set up quick payment feature demo", e);
        }
    }

}
