package com.zapp.core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * ValidationUtils unit tests.
 */
public class ValidationUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testRequireInNullObject() throws ZappModelValidationException {
        ValidationUtils.requireIn(null, null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireInNullRange() throws ZappModelValidationException {
        ValidationUtils.requireIn("", null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireInEmptyRange() throws ZappModelValidationException {
        ValidationUtils.requireIn("", new String[]{}, "");
    }

    @Test
    public void testIsInNullObject() {
        assertFalse(ValidationUtils.isIn(null, null));
    }

    @Test
    public void testIsInNullRange() {
        assertFalse(ValidationUtils.isIn("", null));
    }

    @Test
    public void testIsInEmptyRange() {
        assertFalse(ValidationUtils.isIn("", new String[]{}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireCombinationInNullCombination() throws ZappModelValidationException {
        ValidationUtils.requireCombinationIn(null, new Object[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireCombinationInNullRange() throws ZappModelValidationException {
        ValidationUtils.requireCombinationIn(new Object[]{}, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireCombinationInEmptyRange() throws ZappModelValidationException {
        ValidationUtils.requireCombinationIn(new Object[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireCombinationInDifferentRangeCombinations() throws ZappModelValidationException {
        ValidationUtils.requireCombinationIn(new Object[]{"", ""}, new Object[]{""});
    }

    @Test
    public void testRequireNull() throws ZappModelValidationException {
        ValidationUtils.requireNull(null, null);
    }

    @Test(expected = ZappModelValidationException.class)
    public void testRequireNullFails() throws ZappModelValidationException {
        ValidationUtils.requireNull("", null);
    }

    @Test
    public void testRequireValidAmount() throws ZappModelValidationException {
        ValidationUtils.requireValidAmount_14_2("1.11", null);
        ValidationUtils.requireValidAmount_14_2("12345678912345.11", null);
    }

    @Test(expected = ZappModelValidationException.class)
    public void testRequireValidAmountFails() throws ZappModelValidationException {
        ValidationUtils.requireValidAmount_14_2("", null);
        ValidationUtils.requireValidAmount_14_2(".1", null);
        ValidationUtils.requireValidAmount_14_2(".11", null);
        ValidationUtils.requireValidAmount_14_2(".111", null);
        ValidationUtils.requireValidAmount_14_2("12345678912345.1", null);
        ValidationUtils.requireValidAmount_14_2("12345678912345.111", null);
        ValidationUtils.requireValidAmount_14_2("123456789123456.11", null);
    }

    @Test
    public void testRequireString() throws ZappModelValidationException {
        ValidationUtils.require("s", null);
    }

    @Test(expected = ZappModelValidationException.class)
    public void testRequireStringFails() throws ZappModelValidationException {
        final String s = null;
        ValidationUtils.require("", null);
        ValidationUtils.require(s, null);
    }

    @Test
    public void testRequireObject() throws ZappModelValidationException {
        ValidationUtils.require(new Object(), null);
    }

    @Test(expected = ZappModelValidationException.class)
    public void testRequireObjectFails() throws ZappModelValidationException {
        final Object o = null;
        ValidationUtils.require(o, null);
    }

    @Test
    public void testRequireCondition() throws ZappModelValidationException {
        ValidationUtils.require(true, null);
    }

    @Test(expected = ZappModelValidationException.class)
    public void testRequireConditionFails() throws ZappModelValidationException {
        ValidationUtils.require(false, null);
    }

    @Test
    public void testRequireValidEmail() throws ZappModelValidationException {
        ValidationUtils.requireValidEmail("some@email.com", null);
    }

    @Test(expected = ZappModelValidationException.class)
    public void testRequireValidEmailFails() throws ZappModelValidationException {
        ValidationUtils.requireValidEmail("", null);
        ValidationUtils.requireValidEmail("some", null);
        ValidationUtils.requireValidEmail("@", null);
        ValidationUtils.requireValidEmail("@email", null);
        ValidationUtils.requireValidEmail("@email.com", null);
    }
}
