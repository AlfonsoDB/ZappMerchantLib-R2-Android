package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Transaction Id test BDD test code.
 *
 * @author msagi
 */
public class TestTransactionId {

    private String aptId;
    private String aptrId;
    private TransactionId transactionId;
    private ZappModelValidationException exception;

    @Given("^the APT Id of the transaction id is \"([^\"]*)\"$")
    public void the_APT_Id_of_the_transaction_id_is(String arg1) throws Throwable {
        aptId = arg1;
    }

    @And("^the APTR Id of the transaction id is \"([^\"]*)\"$")
    public void the_APTR_Id_of_the_transaction_id_is(String arg1) throws Throwable {
        aptrId = arg1;
    }

    @When("^I try to create the Transaction Id$")
    public void I_try_to_create_the_Transaction_Id() throws Throwable {
        try {
            transactionId = new TransactionId(aptId, aptrId);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the Transaction Id is created successfully$")
    public void the_transaction_id_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create transaction id:" + (exception == null ? "" : ' ' + exception.getMessage()), transactionId);
    }

    @Then("^the Transaction Id is not created$")
    public void the_Transaction_Id_is_not_created() throws Throwable {
        assertNull("The transaction id is created:", transactionId);
    }
}
