package com.zapp.core;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Address test BDD test code.
 * @author msagi
 */
public class TestAddress {

    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String line5;
    private String line6;
    private String postCode;
    private String countryCode;
    private Address address;
    private ZappModelValidationException exception;

    @Given("^the Line 1 is \"([^\"]*)\"$")
    public void the_Line_1_is(String arg1) throws Throwable {
        line1 = arg1;
    }

    @And("^the Line 2 is \"([^\"]*)\"$")
    public void the_Line_2_is(String arg1) throws Throwable {
        line2 = arg1;
    }

    @And("^the Line 3 is \"([^\"]*)\"$")
    public void the_Line_3_is(String arg1) throws Throwable {
        line3 = arg1;
    }

    @And("^the Line 4 is \"([^\"]*)\"$")
    public void the_Line_4_is(String arg1) throws Throwable {
        line4 = arg1;
    }

    @And("^the Line 5 is \"([^\"]*)\"$")
    public void the_Line_5_is(String arg1) throws Throwable {
        line5 = arg1;
    }

    @And("^the Line 6 is \"([^\"]*)\"$")
    public void the_Line_6_is(String arg1) throws Throwable {
        line6 = arg1;
    }

    @And("^the Post Code is \"([^\"]*)\"$")
    public void the_Post_Code_is(String arg1) throws Throwable {
        postCode = arg1;
    }

    @And("^the Country Code is \"([^\"]*)\"$")
    public void the_Country_Code_is(String arg1) throws Throwable {
        countryCode = arg1;
    }

    @When("^I try to create the Address$")
    public void I_try_to_create_the_Address() throws Throwable {
        try {
            address = new Address(line1, line2, line3, line4, line5, line6, postCode, countryCode);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^Address is created successfully$")
    public void Address_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create address:" + (exception == null ? "" : ' ' + exception.getMessage()), address);
    }

    @Then("^Address is not created$")
    public void Address_is_not_created() throws Throwable {
        assertNull("Address is created:", address);
    }
}
