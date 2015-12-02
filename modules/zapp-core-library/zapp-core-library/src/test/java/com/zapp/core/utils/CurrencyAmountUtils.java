package com.zapp.core.utils;

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
}
