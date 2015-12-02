package com.zapp.core;

import com.zapp.core.generator.AmountDetailFeatureFileGenerator;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * AmountDetail test BDD test code.
 *
 * @author nsevciuc
 */
public class TestAmountDetail {

    /**
     * The amount type.
     */
    private AmountType type = null;

    /**
     * The amount description.
     */
    private String description = null;

    /**
     * The price.
     */
    private String price = null;

    /**
     * The rate.
     */
    private String rate = null;

    /**
     * The Amount detail.
     */
    private AmountDetail amountDetail = null;

    /**
     * The exception happened when payment request is created.
     */
    private ZappModelValidationException exception = null;

    @Given("^the Amount Type is \"([^\"]*)\"$")
    public void the_Amount_Type_is(String arg1) throws Throwable {
        type = AmountType.valueOf(arg1);
    }

    @And("^the Description is \"([^\"]*)\"$")
    public void the_Description_is(String arg1) throws Throwable {
        description = arg1;
    }

    @And("^the Price is \"([^\"]*)\"$")
    public void the_Price_is(String arg1) throws Throwable {
        price = getAmount(arg1);
    }

    @And("^the Rate is \"([^\"]*)\"$")
    public void the_Rate_is(String arg1) throws Throwable {
        rate = getAmount(arg1);
    }

    @When("^I try to create the Amount Detail")
    public void I_try_to_create_the_Amount_Detail() throws Throwable {
        try {
            amountDetail = new AmountDetail(type, description, price, rate);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^Amount Detail is created successfully$")
    public void Amount_Detail_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create amount detail:" + (exception == null ? "" : ' ' + exception.getMessage()), amountDetail);
    }

    @Then("^Amount Detail is not created$")
    public void Amount_Detail_is_not_created() throws Throwable {
        assertNull("Amount detail is created:", amountDetail);
    }

    private String getAmount(final String arg1) {
        String amount = null;
        final AmountDetailFeatureFileGenerator.AmountStatus status = AmountDetailFeatureFileGenerator.AmountStatus.valueOf(arg1);
        switch (status) {
            case is_null:
                amount = null;
                break;
            case negative:
                amount = "-10";
                break;
            case zero:
                amount = "0";
                break;
            case positive:
                amount = "10";
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
        return amount;
    }
}
