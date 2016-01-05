package com.zapp.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Converter utility class to convert between various formats.
 */
public class AmountConverter {

    /**
     * Math context for amount conversion with EXACT precision and NO ROUNDING.
     */
    private static final MathContext AMOUNT_CONVERSION_MATH_CONTEXT = new MathContext(/* precision */ 0, RoundingMode.UNNECESSARY);

    /**
     * Regular expression to match string amounts before converting to integers.
     */
    private static final String AMOUNT_STRING_PATTERN_REGEXP = "[0-9]{1,14}(\\.[0-9]{1,2})*";

    /**
     * Pattern to match string amounts before converting to integers.
     */
    private static final Pattern AMOUNT_STRING_PATTERN = Pattern.compile(AMOUNT_STRING_PATTERN_REGEXP);

    /**
     * Hidden constructor for utility class.
     */
    private AmountConverter() {
    }

    /**
     * Converts the integer amount to string format.
     *
     * @param amount The long amount to be converted. The last two digits is considered to be the fractions, e.g. 1 -> "0.01", 230 -> "2.30", 500 -> "5.00".
     * @return The string amount converted from the given parameter.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public static String toStringAmount(final Long amount) {
        if (amount == null) {
            return null;
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount < 0");
        }
        final BigDecimal bigDecimal = new BigDecimal(amount, AMOUNT_CONVERSION_MATH_CONTEXT).movePointLeft(2);
        return bigDecimal.toString();
    }

    /**
     * Converts the string amount to long format.
     *
     * @param amount The string amount to be converted. The string must have two fraction digits (e.g. "2.29").
     *               The last two digits is considered to be the fractions, e.g. "0.01" -> 1, "4.15" -> 415, "2.05" -> 205.
     * @return The long amount converted from the given parameter.
     * @throws IllegalArgumentException if the amount string is not formatted correctly.
     * @see #AMOUNT_STRING_PATTERN_REGEXP
     */
    public static Long fromStringAmount(final String amount) {
        if (amount == null || amount.isEmpty()) {
            return null;
        }

        if (!AMOUNT_STRING_PATTERN.matcher(amount).matches()) {
            throw new IllegalArgumentException("amount format must match " + AMOUNT_STRING_PATTERN_REGEXP);
        }

        final BigDecimal decimal = new BigDecimal(amount);
        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setGroupingUsed(false);

        final String decimalFormatResult = decimalFormat.format(decimal);

        final BigDecimal bigDecimal = new BigDecimal(decimalFormatResult, AMOUNT_CONVERSION_MATH_CONTEXT).movePointRight(2);
        final long value = bigDecimal.longValue();
        if (value <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        return value;
    }
}