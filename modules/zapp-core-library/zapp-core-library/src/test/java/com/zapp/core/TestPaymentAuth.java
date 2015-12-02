package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Payment auth test BDD test code.
 *
 * @author msagi
 */
public class TestPaymentAuth {

    /**
     * The transaction status.
     */
    private TransactionStatus transactionStatus;

    /**
     * Represents if it is a Normal(Standard) or quick payment.
     */
    private CheckoutType checkoutType;

    /**
     * The payment delivery type.
     */
    private DeliveryType deliveryType;

    /**
     * The consumers bank account on which the transactions should be made.
     */
    private BankAccount bankAccount;

    /**
     * The consumer which makes the payment.
     */
    private User user;

    /**
     * The currency amount.
     */
    private CurrencyAmount finalAmount;

    /**
     * The delivery address.
     */
    private DeliveryAddress deliveryAddress;

    /**
     * The payment auth.
     */
    private PaymentAuth paymentAuth = null;

    /**
     * The exception happened when payment auth is created.
     */
    private ZappModelValidationException exception;

    /**
     * Used to notify Zapp if the user prefers the pay-connect feature.
     */
    private Boolean payconnectEnabled;

    @Given("^the Transaction Status for payment auth is \"([^\"]*)\"$")
    public void the_Transaction_Status_for_payment_auth_is(String arg1) throws Throwable {
        transactionStatus = TransactionStatus.valueOf(arg1);
    }

    @And("^the Checkout Type for payment auth is \"([^\"]*)\"$")
    public void the_Checkout_Type_for_payment_auth_is(String arg1) throws Throwable {
        checkoutType = CheckoutType.valueOf(arg1);
    }

    @And("^the Delivery Type for payment auth is \"([^\"]*)\"$")
    public void the_Delivery_Type_for_payment_auth_is(String arg1) throws Throwable {
        deliveryType = DeliveryType.valueOf(arg1);
    }

    @And("^the Bank Account for payment auth is \"([^\"]*)\"$")
    public void the_Bank_Account_for_payment_auth_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                bankAccount = Defaults.defaultValue(BankAccount.class);
                break;
            case invalid:
                bankAccount = Defaults.defaultInvalidValue(BankAccount.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the User for payment auth is \"([^\"]*)\"$")
    public void the_User_for_payment_auth_is(String arg1) throws Throwable {
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

    @And("^the Final Amount for payment auth is \"([^\"]*)\"$")
    public void the_Final_Amount_for_payment_auth_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                finalAmount = Defaults.defaultValue(CurrencyAmount.class);
                break;
            case invalid:
                finalAmount = Defaults.defaultInvalidValue(CurrencyAmount.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Delivery Address for payment auth is \"([^\"]*)\"$")
    public void the_Delivery_Address_for_payment_auth_is(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                deliveryAddress = Defaults.defaultValue(DeliveryAddress.class);
                break;
            case invalid:
                deliveryAddress = Defaults.defaultInvalidValue(DeliveryAddress.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @Given("^the Transaction Payconnect Enabled is \"(.*?)\"$")
    public void the_Transaction_Payconnect_Enabled_is(String arg1) throws Throwable {
        payconnectEnabled = arg1 == null ? null : Boolean.valueOf(arg1);
    }

    @When("^I try to create the Payment Auth")
    public void I_try_to_create_the_Payment_Auth() throws Throwable {
        try {
            paymentAuth = new PaymentAuth(checkoutType, deliveryType, bankAccount, user, finalAmount, deliveryAddress, transactionStatus, payconnectEnabled);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the Payment Auth is created successfully$")
    public void the_Payment_Auth_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create payment auth:" + (exception == null ? "" : ' ' + exception.getMessage()), paymentAuth);
    }

    @Then("^the Payment Auth is not created$")
    public void the_Payment_Auth_is_not_created() throws Throwable {
        assertNull("Payment auth is created:", paymentAuth);
    }
}
