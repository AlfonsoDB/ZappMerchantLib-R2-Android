package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User test BDD test code.
 * @author msagi
 */
public class TestUser {

    private String firstName;
    private String email;
    private String title;
    private String middleName;
    private String lastName;
    private String phone;
    private User user;
    private ZappModelValidationException exception;

    @Given("^the user's First Name is \"([^\"]*)\"$")
    public void the_users_First_Name_is(String arg1) throws Throwable {
        firstName = arg1;
    }

    @And("^the user's Title is \"([^\"]*)\"$")
    public void the_users_Title_is(String arg1) throws Throwable {
        title = arg1;
    }

    @And("^the user's Middle Name is \"([^\"]*)\"$")
    public void the_users_Middle_Name_is(String arg1) throws Throwable {
        middleName = arg1;
    }

    @And("^the user's Last Name is \"([^\"]*)\"$")
    public void the_users_Last_Name_is(String arg1) throws Throwable {
        lastName = arg1;
    }

    @And("^the user's Email Address is \"([^\"]*)\"$")
    public void the_users_email_address_is(String arg1) throws Throwable {
        email = arg1;
    }

    @And("^the user's Phone Number is \"([^\"]*)\"$")
    public void the_users_Phone_Number_is(String arg1) throws Throwable {
        phone = arg1;
    }

    @When("^I try to create the User")
    public void I_try_to_create_the_User() throws Throwable {
        try {
            user = new User(firstName, lastName, middleName, title, email, phone);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the User is created successfully$")
    public void the_User_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create user:" + (exception == null ? "" : ' ' + exception.getMessage()), user);
    }

    @Then("^the User is not created$")
    public void the_User_is_not_created() throws Throwable {
        assertNull("User is created:", user);
    }
}
