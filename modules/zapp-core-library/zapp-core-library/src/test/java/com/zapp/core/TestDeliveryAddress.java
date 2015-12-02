package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Delivery Address test BDD test code.
 * @author msagi
 */
public class TestDeliveryAddress {

    private String identifier;
    private DeliveryAddressType type;
    private Address address;
    private User addresseeUser;
    private DeliveryAddress deliveryAddress;
    private ZappModelValidationException exception;

    @Given("^the Identifier is \"([^\"]*)\"$")
    public void the_Identifier_is(String arg1) throws Throwable {
        identifier = arg1;
    }

    @And("^the Delivery Address Type is \"([^\"]*)\"$")
    public void the_Delivery_Address_Type_is(String arg1) throws Throwable {
        type = DeliveryAddressType.valueOf(arg1);
    }

    @And("^the Address to deliver is \"([^\"]*)\"$")
    public void the_Address_to_deliver_is(String arg1) throws Throwable {
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

    @And("^the Addressee User is \"([^\"]*)\"$")
    public void the_Addressee_User_is(String arg1) throws Throwable {
        final ParameterStatus parameterStatus = ParameterStatus.valueOf(arg1);
        switch (parameterStatus) {

            case valid:
                addresseeUser = Defaults.defaultValue(User.class);
                break;
            case invalid:
                addresseeUser = Defaults.defaultInvalidValue(User.class);
                break;
            case is_null:
                addresseeUser = null;
                break;
        }
    }

    @When("^I try to create the Delivery Address$")
    public void I_try_to_create_the_Delivery_Address() throws Throwable {
        try {
            deliveryAddress = new DeliveryAddress(identifier, type, addresseeUser, address.getLine1(), address.getLine2(), address.getLine3(), address.getLine4(), address.getLine5(), address.getLine6(), address.getPostCode(), address.getCountryCode());
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the Delivery Address is created successfully$")
    public void the_Delivery_Address_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create Delivery Address:" + (exception == null ? "" : ' ' + exception.getMessage()), deliveryAddress);
    }

    @Then("^the Delivery Address is not created$")
    public void the_Delivery_Address_is_not_created() throws Throwable {
        assertNull("Delivery Address is created:", deliveryAddress);
    }

    @When("^I try to create a Delivery Address using setters$")
    public void i_try_to_create_a_Delivery_Address_using_setters() throws Throwable {
        deliveryAddress = Defaults.defaultValue(DeliveryAddress.class);
        deliveryAddress.setIdentifier(identifier);
        assertEquals("Incorrect setter implementation for delivery address identifier", identifier, deliveryAddress.getIdentifier());
        deliveryAddress.setType(type);
        assertEquals("Incorrect setter implementation for delivery address type", type, deliveryAddress.getType());
        deliveryAddress.setAddressee(addresseeUser);
        assertEquals("Incorrect setter implementation for delivery address addressee", addresseeUser, deliveryAddress.getAddressee());
    }
}
