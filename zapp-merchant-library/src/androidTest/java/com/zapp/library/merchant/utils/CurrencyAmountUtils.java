package com.zapp.library.merchant.utils;

import com.zapp.core.CurrencyAmount;
import com.zapp.core.ZappModelValidationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Utility class for CurrencyAmount data object.
 */
public final class CurrencyAmountUtils {

    /**
     * Math context for amount conversion with EXACT precision and NO ROUNDING.
     */
    private static final MathContext AMOUNT_CONVERSION_MATH_CONTEXT = new MathContext(/* precision */ 0, RoundingMode.UNNECESSARY);

    /**
     * Hidden constructor for utility class.
     */
    private CurrencyAmountUtils() {
    }

    /**
     * Get currency amount from string formatted as "value currencyCode" (e.g. "2.35 GBP")
     *
     * @param s The string value to be parsed.
     * @return The currency amount equivalent of the string or null if string cannot be parsed.
     * @throws ZappModelValidationException If the string can be parsed but the parsed values are invalid
     */
    public static CurrencyAmount fromTestString(final String s) throws ZappModelValidationException {
        if (s == null) {
            return null;
        }

        final String[] parameters = s.split(" ");
        if (parameters.length != 2) {
            return null;
        }

        final BigDecimal rawValue = new BigDecimal(parameters[0], AMOUNT_CONVERSION_MATH_CONTEXT).movePointRight(2);
        final Long value = rawValue.longValue();
        final String currencyCode = parameters[1];
        return new CurrencyAmount(value, currencyCode);
    }

    /**
     * Convert currency amount value to double.
     *
     * @param currencyAmount The currency amount to convert.
     * @return The double value of the currency amount.
     */
    public static double toDouble(final CurrencyAmount currencyAmount) {
        if (currencyAmount == null) {
            throw new IllegalArgumentException("currencyAmount == null");
        }
        try {
            currencyAmount.validate();
        } catch (ZappModelValidationException zmve) {
            throw new IllegalArgumentException("!currencyAmount.validate()");
        }
        return (double) (currencyAmount.getValue() / 100);
    }

    /**
     * Convert double formatted string to int to use with {@link CurrencyAmount} as value.
     *
     * @param doubleString The double formatted string to use, e.g. 2.30
     * @return the {@link CurrencyAmount} value.
     */
    public static int fromDoubleString(final String doubleString) {
        final double d = Double.parseDouble(doubleString);
        return (int) d * 100;
    }

    /**
     * Convert double to int to use with {@link CurrencyAmount} as value.
     *
     * @param d The double to use, e.g. 2.30
     * @return the {@link CurrencyAmount} value.
     */
    public static int fromDouble(final double d) {
        return (int) d * 100;
    }
}
