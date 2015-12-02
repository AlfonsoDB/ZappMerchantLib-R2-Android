package com.zapp.core;

import com.zapp.core.generator.TransactionFeatureFileGenerator;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Transaction test BDD test code.
 *
 * @author msagi
 */
public class TestTransaction {

    /**
     * The payment status for a specific point in time.
     */
    private TransactionStatus transactionStatus;

    /**
     * The 6 character Zapp Code / Basket Reference Number for entry in the mobile banking app on the consumer’s device.
     */
    private String brn;

    /**
     * The payment transaction id.
     */
    private TransactionId transactionId;

    /**
     * The payment request details.
     */
    private PaymentRequest paymentRequest;

    /**
     * The payment authorisation details.
     */
    private PaymentAuth paymentAuth;

    /**
     * The transaction {@link MessageType} message type.
     */
    private MessageType messageType;

    /**
     * The time interval defined by the zapp core in order to confirm the payment.
     */
    private Integer confirmationExpiryInterval;

    /**
     * The time interval defined by the zapp core in order to retrieve the payment details.
     */
    private Integer retrievalExpiryInterval;

    /**
     * The payment authorisation details retrieval method.
     */
    private RetrievalMethod retrievalMethod;

    /**
     * Represents the custom scheme used by an android intent in order to open the merchant app after the bank app finishes to process the transaction.
     */
    private String merchantCallBackUrl;

    /**
     * Used to know when the consumer should be asked about payconnect.
     */
    private Boolean askForPayconnect;

    /**
     * Is used to know if the device uses payconnect.
     */
    private Boolean initiatorIsAuthorizer;

    /**
     * The identifier that is obtained when the payment is finished.
     */
    private String settlementRetrievalId;

    /**
     * Indicates if a payment notification was sent.
     */
    private Boolean notificationSent;

    /**
     * The transaction.
     */
    private Transaction transaction = null;

    /**
     * The exception happened when transaction is created.
     */
    private ZappModelValidationException exception = null;

    @And("^the Message Type of the transaction is \"([^\"]*)\"$")
    public void the_Message_Type_of_the_transaction_is(String arg1) throws Throwable {
        messageType = MessageType.valueOf(arg1);
    }

    @And("^the Retrieval Method of the transaction is \"([^\"]*)\"$")
    public void the_Retrieval_Method_of_the_transaction_is(String arg1) throws Throwable {
        retrievalMethod = RetrievalMethod.valueOf(arg1);
    }

    @And("^the Retrieval Expiry Interval of the transaction is \"([^\"]*)\"$")
    public void the_Retrieval_Expiry_Interval_of_the_transaction_is(String arg1) throws Throwable {
        retrievalExpiryInterval = new Integer(arg1);
    }

    @Given("^the Settlement Retrieval Id of the transaction is \"(.*?)\"$")
    public void the_Settlement_Retrieval_Id_of_the_transaction_is(String arg1) throws Throwable {
        settlementRetrievalId = arg1;
    }

    @And("^the Notification Sent status of the transaction is \"([^\"]*)\"$")
    public void the_Notification_Sent_status_of_the_transaction_is(String arg1) throws Throwable {
        notificationSent = Boolean.valueOf(arg1);
    }

    @And("^the Transaction Id of the transaction is \"([^\"]*)\"$")
    public void the_Transaction_Id_of_the_transaction_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                transactionId = Defaults.defaultValue(TransactionId.class);
                break;
            case invalid:
                transactionId = Defaults.defaultInvalidValue(TransactionId.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Payment Request of the transaction is \"([^\"]*)\"$")
    public void the_Payment_Request_of_the_transaction_is(String arg1) throws Throwable {
        final TransactionFeatureFileGenerator.PaymentRequestStatus status = TransactionFeatureFileGenerator.PaymentRequestStatus.valueOf(arg1);
        switch (status) {
            case is_immediate:
                paymentRequest = new PaymentRequest(RTPType.IMMEDIATE, PaymentType.INSTANT_PAYMENT, CheckoutType.NORMAL, DeliveryType.ADDRESS, Defaults.getDefaultUser(),
                        null, Defaults.getDefaultCurrencyAmount(), Defaults.getDefaultMerchant(), Defaults.getDefaultAddress(), "merchantCallBackUrl", null, null, null,
                        null, null);
                break;
            case is_deferred:
                paymentRequest = new PaymentRequest(RTPType.DEFERRED, PaymentType.INSTANT_PAYMENT, CheckoutType.NORMAL, DeliveryType.ADDRESS, Defaults.getDefaultUser(),
                        null, null, Defaults.getDefaultMerchant(), Defaults.getDefaultAddress(), "merchantCallBackUrl", ACRType.NONE, null,
                        Defaults.getDefaultCurrencyAmount(), Defaults.getDefaultCurrencyAmount(), null);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Payment Auth of the transaction is \"([^\"]*)\"$")
    public void the_Payment_Auth_of_the_transaction_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                paymentAuth = Defaults.defaultValue(PaymentAuth.class);
                break;
            case invalid:
                paymentAuth = Defaults.defaultInvalidValue(PaymentAuth.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Zapp Code of the transaction is \"([^\"]*)\"$")
    public void the_Zapp_Code_of_the_transaction_is(String arg1) throws Throwable {
        brn = arg1;
    }

    @And("^the Confirmation Expiry Interval of the transaction is \"([^\"]*)\"$")
    public void the_Confirmation_Expiry_Interval_of_the_transaction_is(String arg1) throws Throwable {
        if (arg1 == null) {
            confirmationExpiryInterval = null;
        } else {
            confirmationExpiryInterval = new Integer(arg1);
        }
    }

    @Given("^the Ask for PayConnect of the transaction is \"(.*?)\"$")
    public void the_Ask_for_PayConnect_of_the_transaction_is(String arg1) throws Throwable {
        askForPayconnect = arg1 == null ? null : Boolean.valueOf(arg1);
    }

    @Given("^the Initiator is Authorizer of PayConnect of the transaction is \"(.*?)\"$")
    public void the_Initiator_is_Authorizer_of_PayConnect_of_the_transaction_is(String arg1) throws Throwable {
        initiatorIsAuthorizer = arg1 == null ? null : Boolean.valueOf(arg1);
    }

    @When("^I try to create the Transaction$")
    public void I_try_to_create_the_Transaction() throws Throwable {
        try {
            transaction = new Transaction(brn, transactionId, paymentRequest, paymentAuth, retrievalExpiryInterval, confirmationExpiryInterval, retrievalMethod,
                    merchantCallBackUrl, askForPayconnect, initiatorIsAuthorizer, settlementRetrievalId, notificationSent);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the Transaction is created successfully$")
    public void the_Transaction_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create transaction:" + (exception == null ? "" : ' ' + exception.getMessage()), transaction);
    }

    @Then("^the Transaction is not created$")
    public void the_Transaction_is_not_created() throws Throwable {
        assertNull("Transaction is created:", transaction);
    }
}
