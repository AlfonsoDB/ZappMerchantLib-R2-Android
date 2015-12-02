package com.zapp.core;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Address test BDD test code.
 *
 * @author msagi
 */
public class TestBankAccount {
    /**
     * CFI Account Number.
     */
    private String number;

    /**
     * Account name.
     */
    private String name;

    /**
     * CFI Sort Code.
     */
    private String sortCode;

    /**
     * Balance.
     */
    private CurrencyAmount balance;

    /**
     * The available funds.
     */
    private CurrencyAmount availableFunds;

    /**
     * The bank account.
     */
    private BankAccount bankAccount;

    /**
     * The exception happened during creating bank account (if any).
     */
    private ZappModelValidationException exception;

    @Given("^the Number is \"([^\"]*)\"$")
    public void the_Number_is(final String arg1) throws Throwable {
        number = arg1;
    }

    @And("^the Name is \"([^\"]*)\"$")
    public void the_Name_is(String arg1) throws Throwable {
        name = arg1;
    }

    @And("^the Sort Code is \"([^\"]*)\"$")
    public void the_Sort_Code_is(String arg1) throws Throwable {
        sortCode = arg1;
    }

    @And("^the Balance is \"([^\"]*)\"$")
    public void the_Balance_is(final String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                balance = Defaults.defaultValue(CurrencyAmount.class);
                break;
            case invalid:
                balance = Defaults.defaultInvalidValue(CurrencyAmount.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @And("^the Available Funds are \"([^\"]*)\"$")
    public void the_Available_Funds_are(String arg1) throws Throwable {
        final ParameterStatus status = ParameterStatus.valueOf(arg1);
        switch (status) {
            case valid:
                availableFunds = Defaults.defaultValue(CurrencyAmount.class);
                break;
            case invalid:
                availableFunds = Defaults.defaultInvalidValue(CurrencyAmount.class);
                break;
            default:
                fail("Invalid parameter: " + arg1);
        }
    }

    @When("^I try to create the Bank Account$")
    public void I_try_to_create_the_Bank_Account() {
        try {
            bankAccount = new BankAccount(number, name, sortCode, balance, availableFunds);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^Bank Account is created successfully$")
    public void Bank_Account_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create bank account:" + (exception == null ? "" : ' ' + exception.getMessage()), bankAccount);
    }

    @Then("^The Bank Account is not created$")
    public void The_Bank_Account_is_not_created() throws Throwable {
        assertNull("Bank account is created:", bankAccount);
    }
}
