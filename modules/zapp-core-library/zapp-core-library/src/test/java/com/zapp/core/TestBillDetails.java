package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Address test BDD test code.
 *
 * @author msagi
 */
public class TestBillDetails {

    /**
     * Date formatter.
     */
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Bill Payment Consumer Account Identifier.
     */
    private String accountId;

    /**
     * Biller / Bill Reference.
     */
    private String reference;

    /**
     * Bill Pay Billing Period From [yyyy-MM-dd].
     */
    private Date periodFrom;

    /**
     * Bill Pay Billing Period To [yyyy-MM-dd].
     */
    private Date periodTo;

    /**
     * The bill details.
     */
    private BillDetails billDetails;

    /**
     * The exception happened during creating bill details (if any).
     */
    private Exception exception;

    @Given("^the Account Id is \"([^\"]*)\"$")
    public void the_Account_Id_is(String arg1) throws Throwable {
        accountId = arg1;
    }

    @And("^the Reference is \"([^\"]*)\"$")
    public void the_Reference_is(String arg1) throws Throwable {
        reference = arg1;
    }

    @And("^the Period From is \"([^\"]*)\"$")
    public void the_Period_From_is(String arg1) throws Throwable {
        periodFrom = DATE_FORMAT.parse(arg1);
        assertNotNull(periodFrom);
    }

    @And("^the Period To is \"([^\"]*)\"$")
    public void the_Period_To_is(String arg1) throws Throwable {
        periodTo = DATE_FORMAT.parse(arg1);
        assertNotNull(periodTo);
    }

    @When("^I try to create the Bill Details$")
    public void I_try_to_create_the_Bill_Details() throws Throwable {
        try {
            billDetails = new BillDetails(accountId, reference, periodFrom, periodTo);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^The Bill Details is created successfully$")
    public void The_Bill_Details_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create bill details:" + (exception == null ? "" : ' ' + exception.getMessage()), billDetails);
    }

    @Then("^The Bill Details is not created$")
    public void The_Bill_Details_is_not_created() throws Throwable {
        assertNull("Bill details is created:", billDetails);
    }
}
