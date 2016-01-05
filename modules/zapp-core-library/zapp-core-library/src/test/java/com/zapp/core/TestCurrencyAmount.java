package com.zapp.core;

import com.zapp.core.utils.CurrencyAmountUtils;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Currency Amount test BDD test code.
 *
 * @author msagi
 */
public class TestCurrencyAmount {

    private Long value;

    private String currencyCode;

    private CurrencyAmount currencyAmount;

    private CurrencyAmount currencyAmount2;

    private Exception exception;

    @Given("^the Value is \"([^\"]*)\"$")
    public void the_Value_is(String arg1) throws Throwable {
        if (arg1 == null) {
            value = null;
        }
        value = new Long(arg1);
    }

    @And("^the Currency Code is \"([^\"]*)\"$")
    public void the_Currency_Code_is(String arg1) throws Throwable {
        currencyCode = arg1;
    }

    @When("^I try to create the Currency Amount$")
    public void I_try_to_create_the_Currency_Amount() throws Throwable {
        try {
            currencyAmount = new CurrencyAmount(value, currencyCode);
        } catch (ZappModelValidationException zmve) {
            exception = zmve;
        }
    }

    @Then("^the Currency Amount is created successfully$")
    public void the_Currency_Amount_is_created_successfully() throws Throwable {
        assertNotNull("Failed to create currency amount:" + (exception == null ? "" : ' ' + exception.getMessage()), currencyAmount);
    }

    @Then("^the Currency Amount is not created$")
    public void the_Currency_Amount_is_not_created() throws Throwable {
        assertNull("Currency Amount is created:", currencyAmount);
    }

    @And("^the Display String of the Currency Amount is \"([^\"]*)\"$")
    public void the_Display_String_of_the_Currency_Amount_is(String arg1) throws Throwable {
        assertNotNull(currencyAmount);
        final String displayString = currencyAmount.toString();
        assertEquals(displayString, arg1);
    }

    @And("^the Display String of the Currency Amount with pence formatting is \"([^\"]*)\"$")
    public void the_Display_String_of_the_Currency_Amount_with_pence_formatting_is(String arg1) throws Throwable {
        assertNotNull(currencyAmount);
        final String displayString = currencyAmount.toStringWithPence();
        assertEquals(displayString, arg1);
    }

    @Given("^the current Currency Amount is \"([^\"]*)\"$")
    public void the_current_Currency_Amount_is(String arg1) throws Throwable {
        try {
            currencyAmount = CurrencyAmountUtils.fromTestString(arg1);
        } catch (ZappModelValidationException zmve) {
            //nothing to do here just drop the exception
        }
        assertNotNull(currencyAmount);
    }

    @When("^I try to add a second currency amount \"([^\"]*)\" to the current Currency Amount$")
    public void I_try_to_add_a_second_currency_amount_to_the_current_Currency_Amount(String arg1) throws Throwable {
        assertNotNull(currencyAmount);
        assertNull(exception);
        try {
            currencyAmount2 = CurrencyAmountUtils.fromTestString(arg1);
            currencyAmount.add(currencyAmount2);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("^the second currency amount is added successfully$")
    public void the_second_currency_amount_is_added_successfully() throws Throwable {
        assertNull(exception);
    }

    @Then("^the second currency amount adding is failed$")
    public void the_second_currency_amount_adding_is_failed() throws Throwable {
        assertNotNull(exception);
    }

    @When("^I try to subtract a second currency amount \"([^\"]*)\" from the current Currency Amount$")
    public void I_try_to_subtract_a_second_currency_amount_from_the_current_Currency_Amount(String arg1) throws Throwable {
        assertNotNull(currencyAmount);
        assertNull(exception);
        try {
            currencyAmount2 = CurrencyAmountUtils.fromTestString(arg1);
            currencyAmount.subtract(currencyAmount2);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("^the second currency amount is subtracted successfully$")
    public void the_second_currency_amount_is_subtracted_successfully() throws Throwable {
        assertNull(exception);
    }

    @Then("^the second currency amount subtracting is failed$")
    public void the_second_currency_amount_subtracting_is_failed() throws Throwable {
        assertNotNull(exception);
    }

    @When("^I try to multiply the currency amount by quantity \"([^\"]*)\"$")
    public void I_try_to_multiply_the_currency_amount_by_quantity(String arg1) throws Throwable {
        assertNotNull(currencyAmount);
        assertNull(exception);
        try {
            final Integer quantity = arg1 == null || arg1.isEmpty() ? null : new Integer(arg1);
            currencyAmount.multiplyByQuantity(quantity);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("^the currency amount is multiplied successfully$")
    public void the_currency_amount_is_multiplied_successfully() throws Throwable {
        assertNull(exception);
    }

    @Then("^the currency amount multiplication is failed$")
    public void the_currency_amount_multiplication_is_failed() throws Throwable {
        assertNotNull(exception);
    }

    @When("^I set the Currency Amount value to null$")
    public void i_set_the_Currency_Amount_value_to_null() throws Throwable {
        currencyAmount.setValue(null);
    }

    @Then("^the Display String of the Currency Amount returns a null value$")
    public void the_Display_String_of_the_Currency_Amount_returns_a_null_value() throws Throwable {
        assertNull(currencyAmount.toString());
    }

    @When("^I set an invalid Currency Code like \"(.*?)\"$")
    public void i_set_an_invalid_Currency_Code_like(String arg1) throws Throwable {
        i_set_to_Currency_Code(arg1);
    }

    @When("^I want to retrieve the Display String$")
    public void i_want_to_retrieve_the_Display_String() throws Throwable {
        try {
            currencyAmount.toString();
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @Then("^an IllegalArgumentException is thrown$")
    public void an_IllegalArgumentException_is_thrown() throws Throwable {
        assertTrue(exception instanceof IllegalArgumentException);
    }

    @Given("^I set \"(.*?)\" to Currency Code$")
    public void i_set_to_Currency_Code(String arg1) throws Throwable {
        currencyAmount.setCurrencyCode("null".equals(arg1) ? null : arg1);
    }
}
