package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Merchant test BDD test code.
 *
 * @author msagi
 */
public class TestMerchant {


    /**
     * The merchant's unique identifier.
     */
    private String identifier;

    /**
     * The merchant's name.
     */
    private String name;

    /**
     * The merchant's email.
     */
    private String email;

    /**
     * The merchant's contact phone.
     */
    private String phone;

    /**
     * The merchant's address.
     */
    private Address address;

    /**
     * The merchant's website.
     */
    private String website;

    /**
     * The merchant.
     */
    private Merchant merchant;

    /**
     * The exception happened during creating the merchant (if any).
     */
    private ZappModelValidationException exception;

    @Given("^the Merchant Identifier is \"([^\"]*)\"$")
    public void the_Merchant_Identifier_is(String arg1) throws Throwable {
        identifier = arg1;
    }

    @And("^the Merchant Name is \"([^\"]*)\"$")
    public void the_Merchant_Name_is(String arg1) throws Throwable {
        name = arg1;
    }

    @And("^the Merchant Email is \"([^\"]*)\"$")
    public void the_Merchant_Email_is(String arg1) throws Throwable {
        email = arg1;
    }

    @And("^the Merchant Phone is \"([^\"]*)\"$")
    public void the_Merchant_Phone_is(String arg1) throws Throwable {
        phone = arg1;
    }

    @And("^the Merchant Address is \"([^\"]*)\"$")
    public void the_Merchant_Address_is(String arg1) throws Throwable {
        final ParameterStatus parameterStatus = ParameterStatus.valueOf(arg1);
        switch (parameterStatus) {

            case valid:
                address = Defaults.defaultValue(Address.class);
                break;
            case invalid:
                address = Defaults.defaultInvalidValue(Address.class);
                break;
            case is_null:
                address = null;
                break;
        }
    }

    @And("^the Merchant Website is \"([^\"]*)\"$")
    public void the_Merchant_Website_is(String arg1) throws Throwable {
        website = arg1;
    }

    @When("^I try to create the Merchant$")
    public void I_try_to_create_the_Merchant() throws Throwable {
        try {
            merchant = new Merchant(identifier, name, email, phone, address, website, /* logoUrl */ null);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the Merchant is created successfully$")
    public void the_Merchant_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create Mechant:" + (exception == null ? "" : ' ' + exception.getMessage()), merchant);
    }

    @Then("^the Merchant is not created$")
    public void the_Merchant_is_not_created() throws Throwable {
        assertNull("Merchant is created:", merchant);
    }
}
