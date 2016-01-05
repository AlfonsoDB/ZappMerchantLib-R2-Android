package com.zapp.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for the AmountConverter utility class.
 */
public class AmountConverterTest {

    // toStringAmount

    @Test
    public void testToStringAmount1() {
        final String amountString = AmountConverter.toStringAmount(null);
        Assert.assertEquals(null, amountString);
    }

    @Test
    public void testToStringAmount2() {
        final String amountString = AmountConverter.toStringAmount(1l);
        Assert.assertEquals("0.01", amountString);
    }

    @Test
    public void testToStringAmount3() {
        final String amountString = AmountConverter.toStringAmount(100l);
        Assert.assertEquals("1.00", amountString);
    }

    @Test
    public void testToStringAmount4() {
        final String amountString = AmountConverter.toStringAmount(229l);
        Assert.assertEquals("2.29", amountString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToStringAmount5() {
        AmountConverter.toStringAmount(-1l);
    }

    // fromStringAmount

    @Test
    public void testFromStringAmount1() {
        final Long amount = AmountConverter.fromStringAmount(null);
        Assert.assertEquals(null, amount);
    }

    @Test
    public void testFromStringAmount2() {
        final Long amount = AmountConverter.fromStringAmount("1");
        Assert.assertEquals(100, amount.longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringAmount3() {
        AmountConverter.fromStringAmount(".9");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringAmount4() {
        AmountConverter.fromStringAmount(".79");
    }

    @Test
    public void testFromStringAmount5() {
        final Long amount = AmountConverter.fromStringAmount("1.1");
        Assert.assertEquals(110, amount.longValue());
    }

    @Test
    public void testFromStringAmount6() {
        final Long amount = AmountConverter.fromStringAmount("11000000.00");
        Assert.assertEquals(1100000000, amount.longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringAmount7() {
        AmountConverter.fromStringAmount("-5.00");
    }

    @Test
    public void testFromStringAmount8() {
        final Long amount = AmountConverter.fromStringAmount("0.01");
        Assert.assertEquals(1, amount.longValue());
    }

    @Test
    public void testFromStringAmount9() {
        final Long amount = AmountConverter.fromStringAmount("2.29");
        Assert.assertEquals(229, amount.longValue());
    }

    @Test
    public void testFromStringAmount10() {
        final Long amount = AmountConverter.fromStringAmount("100000.00");
        Assert.assertEquals(10000000, amount.longValue());
    }
}