package com.zapp.core;

import com.zapp.core.generator.PaymentRequestFeatureFileGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Payment request test BDD test code.
 *
 * @author msagi
 */
public class TestPaymentRequest {

    /**
     * Date formatter.
     */
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYY-MM-DD");

    /**
     * The payment request type.
     */
    private RTPType rtpType = null;

    /**
     * The payment type.
     */
    private PaymentType paymentType = null;

    /**
     * The checkout type.
     */
    private CheckoutType checkoutType = null;

    /**
     * The delivery type.
     */
    private DeliveryType deliveryType = null;

    /**
     * The merchant type.
     */
    private Merchant merchant = null;

    /**
     * The currency amount.
     */
    private CurrencyAmount currencyAmount = null;

    /**
     * The user.
     */
    private User user = null;

    /**
     * The bill details.
     */
    private BillDetails billDetails = null;

    /**
     * The address.
     */
    private Address address = null;

    /**
     * Represents the custom scheme used by an android intent in order to open the merchant app after the bank app finishes to process the transaction.
     */
    private String merchantCallBackUrl = null;

    /**
     * Authentication check required type.
     */
    private ACRType acrType = null;

    /**
     * Deferred Request To Pay Expiry Datetime (format: "YYYY-MM-DD").
     */
    private Date defrdRTPExpDateTime = null;

    /**
     * Deferred Request To Pay Agreement Amount.
     */
    private CurrencyAmount defrdRTPAgrmtAmount = null;

    /**
     * Deferred Request To Pay Maximum Agreed Amount.
     */
    private CurrencyAmount defrdRTPMaxAgrdAmount = null;

    /**
     * Deferred amount details.
     */
    private AmountDetail[] defrdAmountDetails = null;

    /**
     * The payment request.
     */
    private PaymentRequest paymentRequest = null;

    /**
     * The exception happened when payment request is created.
     */
    private ZappModelValidationException exception = null;


    @Given("^the RTP Type is \"([^\"]*)\"$")
    public void the_RTPType_Type_is(String arg1) throws Throwable {
        rtpType = RTPType.valueOf(arg1);
    }

    @And("^the Payment Type is \"([^\"]*)\"$")
    public void the_Payment_Type_is(String arg1) throws Throwable {
        paymentType = PaymentType.valueOf(arg1);
    }

    @And("^the Checkout Type is \"([^\"]*)\"$")
    public void the_Checkout_Type_is(String arg1) throws Throwable {
        checkoutType = CheckoutType.valueOf(arg1);
    }

    @And("^the Delivery Type is \"([^\"]*)\"$")
    public void the_Delivery_Type_is(String arg1) throws Throwable {
        deliveryType = DeliveryType.valueOf(arg1);
    }

    @And("^the Merchant Callback URL of the PaymentRequest is \"([^\"]*)\"$")
    public void the_Callback_URL_of_the_PaymentRequest_is(String arg1) throws Throwable {
        merchantCallBackUrl = arg1;
    }

    @And("^the Authentication check required type is \"([^\"]*)\"$")
    public void the_ARC_Type_is(String arg1) throws Throwable {
        acrType = ACRType.valueOf(arg1);
    }

    @And("^the Deferred request to pay expiry datetime is \"([^\"]*)\"$")
    public void the_Deferred_Request_Expiry_Date_is(String arg1) throws Throwable {
        defrdRTPExpDateTime = DATE_FORMAT.parse(arg1);
    }

    @And("^the Deferred request to pay agreement amount is \"([^\"]*)\"$")
    public void the_Deferred_Request_Agreement_Amount_is(String arg1) throws Throwable {
        defrdRTPAgrmtAmount = getCurrencyAmount(arg1);
    }

    @And("^the Deferred request to pay maximum agreed amount is \"([^\"]*)\"$")
    public void the_Deferred_Request_Maximum_Agreed_Amount_is(String arg1) throws Throwable {
        defrdRTPMaxAgrdAmount = getCurrencyAmount(arg1);
    }

    @And("^the Deferred amount details is \"([^\"]*)\"$")
    public void the_Deferred_Amount_Details_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                defrdAmountDetails = Defaults.defaultValue(AmountDetail[].class);
                break;
            case invalid:
            case is_null:
                defrdAmountDetails = null;
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Bill Details is \"([^\"]*)\"$")
    public void the_Bill_Details_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                billDetails = Defaults.defaultValue(BillDetails.class);
                break;
            case invalid:
                billDetails = Defaults.defaultInvalidValue(BillDetails.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Merchant is \"([^\"]*)\"")
    public void the_Merchant_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                merchant = Defaults.defaultValue(Merchant.class);
                break;
            case invalid:
                merchant = Defaults.defaultInvalidValue(Merchant.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Currency Amount is \"([^\"]*)\"$")
    public void the_Currency_Amount_is_valid(String arg1) throws Throwable {
        currencyAmount = getCurrencyAmount(arg1);
    }

    @And("^the User is \"([^\"]*)\"$")
    public void the_User_is_valid(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                user = Defaults.defaultValue(User.class);
                break;
            case invalid:
                user = Defaults.defaultInvalidValue(User.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Address is \"([^\"]*)\"$")
    public void the_Address_is_valid(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                address = Defaults.defaultValue(Address.class);
                break;
            case invalid:
                address = Defaults.defaultInvalidValue(Address.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @When("^I try to create the Payment Request$")
    public void I_try_to_create_the_Payment_Request() throws Throwable {
        try {
            paymentRequest = new PaymentRequest(rtpType, paymentType, checkoutType, deliveryType, user, billDetails, currencyAmount, merchant, address,
                    "merchantCallBackUrl", acrType, defrdRTPExpDateTime, defrdRTPAgrmtAmount, defrdRTPMaxAgrdAmount, defrdAmountDetails);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^Payment Request is created successfully$")
    public void Payment_Request_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create payment request:" + (exception == null ? "" : ' ' + exception.getMessage()), paymentRequest);
    }

    @Then("^Payment Request is not created$")
    public void Payment_Request_is_not_created() throws Throwable {
        assertNull("Payment request is created:", paymentRequest);
    }

    private CurrencyAmount getCurrencyAmount(final String arg1) throws ZappModelValidationException {
        CurrencyAmount amount = null;
        final PaymentRequestFeatureFileGenerator.CurrencyAmountStatus status = PaymentRequestFeatureFileGenerator.CurrencyAmountStatus.valueOf(arg1);
        switch (status) {
            case is_null:
                amount = null;
                break;
            case invalid:
                amount = Defaults.defaultInvalidValue(CurrencyAmount.class);
                break;
            case negative:
                amount = new CurrencyAmount(-15l, CurrencyAmount.POUNDS);
                break;
            case zero:
                amount = new CurrencyAmount(0l, CurrencyAmount.POUNDS);
                break;
            case positive:
                amount = new CurrencyAmount(25l, CurrencyAmount.POUNDS);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
        return amount;
    }
}
