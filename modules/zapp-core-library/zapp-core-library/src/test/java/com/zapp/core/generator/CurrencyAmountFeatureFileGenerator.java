package com.zapp.core.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Feature file generator for Currency Amount model object.
 *
 * @author msagi
 */
public class CurrencyAmountFeatureFileGenerator extends AbstractFeatureFileGenerator {

    private enum ValueTestValues {
        NULL(null),
        NEGATIVE(-25),
        ZERO(0),
        VALID(123),
        ;
        private final Integer value;
        ValueTestValues(final Integer value) {
            this.value = value;
        }
        public Integer getValue() { return value;}
    }

    private enum CurrencyCodeTestValues {
        NULL(null),
        POUNDS("GBP"),
        EMPTY(""),
        DOLLARS("USD"),
        ;
        private final String currencyCode;
        CurrencyCodeTestValues(final String currencyCode) {
            this.currencyCode = currencyCode;
        }
        public String getCurrencyCode() {
            return currencyCode;
        }
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("value | currencyCode |");
    }

    /**
     * Get scenario type.
     *
     * @param value        The value of the currency amount.
     * @param currencyCode The currency code of the currency amount.
     * @return The type of the scenario.
     */
    public ScenarioType getScenarioType(final ValueTestValues value, final CurrencyCodeTestValues currencyCode) {

        switch (value) {

            case NULL:
                return getNegativeScenarioType(getFingerprint(value.getValue()));
            case NEGATIVE:
            case ZERO:
            case VALID:
                //positive fingerprint is added later
                break;
        }

        switch (currencyCode) {

            case EMPTY:
                return getNegativeScenarioType(getFingerprint(value.value, currencyCode.getCurrencyCode()));
            case NULL:
            case POUNDS:
            case DOLLARS:
                //positive fingerprint is added later
                break;
        }

        addPositiveScenario(getFingerprint(value.getValue(), currencyCode.getCurrencyCode()));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate scenario text.
     *
     * @param value        The value of the currency amount.
     * @param currencyCode The currency code of the currency amount.
     * @return The text of the generated scenario.
     */
    private String generateScenario(final ValueTestValues value, final CurrencyCodeTestValues currencyCode) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(value, currencyCode);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("CurrencyAmount.P%d", getNextPositiveScenarioId());
                expectedResultString = "the Currency Amount is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("CurrencyAmount.N%d", getNextNegativeScenarioId());
                expectedResultString = "the Currency Amount is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (value != ValueTestValues.NULL) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Value is \"%s\"\n", value.getValue()));
        }
        if (currencyCode != CurrencyCodeTestValues.NULL) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Currency Code is \"%s\"\n", currencyCode.getCurrencyCode()));
        }

        stringBuilder.append("    When I try to create the Currency Amount\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    /**
     * Generate BDD test .feature file content.
     *
     * @param bddTestFile The output file to generate content into.
     * @throws FileNotFoundException If given file is invalid.
     */
    @Override
    public void generate(final File bddTestFile) throws FileNotFoundException {

        dumpScenarioFingerprintsHeader();

        final PrintWriter fileWriter = new PrintWriter(bddTestFile);

        fileWriter.println("Feature: Currency Amount\n");

        for (final ValueTestValues value : ValueTestValues.values()) {
            for (final CurrencyCodeTestValues currencyCode : CurrencyCodeTestValues.values()) {
                final String scenario = generateScenario(value, currencyCode);
                fileWriter.print(scenario);
            }
        }

        fileWriter.close();

        dumpGenerationStatistics();

        dumpScenarioFingerprints();
    }

    /**
     * Command line main method.
     *
     * @param args The list of command line arguments.
     */
    public static void main(final String[] args) {

        try {
            final CurrencyAmountFeatureFileGenerator generator = new CurrencyAmountFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + CurrencyAmountFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
