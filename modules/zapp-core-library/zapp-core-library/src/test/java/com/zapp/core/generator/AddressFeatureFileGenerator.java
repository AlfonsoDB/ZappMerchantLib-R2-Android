package com.zapp.core.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Feature file generator for Address model object.
 *
 * @author msagi
 */
public class AddressFeatureFileGenerator extends AbstractFeatureFileGenerator {

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("line1 | countryCode | line2 | line3 | line4 | line5 | line6 | postCode |");
    }

    public ScenarioType getScenarioType(final String line1, final String countryCode, final String line2, final String line3, final String line4,
                                        final String line5, final String line6,
                                        final String postCode) {

        if (!isRequired(line1)) {
            return getNegativeScenarioType(getFingerprint(line1));
        }

        if (countryCode != null && countryCode.isEmpty()) {
            return getNegativeScenarioType(getFingerprint(line1, countryCode));
        }

        addPositiveScenario(getFingerprint(line1, countryCode, line2, line3, line4, line5, line6, postCode));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate scenario text.
     *
     * @param line1       The line 1 of the address.
     * @param countryCode The country code of the address.
     * @param line2       The line 2 of the address.
     * @param line3       The line 3 of the address.
     * @param line4       The line 4 of the address.
     * @param line5       The line 5 of the address.
     * @param line6       The line 6 of the address.
     * @param postCode    The post code of the address.
     * @return The text of the generated scenario.
     */
    private String generateScenario(final String line1, final String countryCode, final String line2, final String line3, final String line4,
                                    final String line5, final String line6,
                                    final String postCode) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(line1, countryCode, line2, line3, line4, line5, line6, postCode);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("Address.P%d", getNextPositiveScenarioId());
                expectedResultString = "Address is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("Address.N%d", getNextNegativeScenarioId());
                expectedResultString = "Address is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (line1 != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Line 1 is \"%s\"\n", line1));
        }
        if (line2 != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Line 2 is \"%s\"\n", line2));
        }
        if (line3 != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Line 3 is \"%s\"\n", line3));
        }
        if (line4 != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Line 4 is \"%s\"\n", line4));
        }
        if (line5 != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Line 5 is \"%s\"\n", line5));
        }
        if (line6 != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Line 6 is \"%s\"\n", line6));
        }
        if (postCode != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Post Code is \"%s\"\n", postCode));
        }
        if (countryCode != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Country Code is \"%s\"\n", countryCode));
        }

        stringBuilder.append("    When I try to create the Address\n");

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

        fileWriter.println("Feature: Address\n");

        final String[] line1Range = {null, "2 Puddle Dock", ""};
        final String[] line2Range = {"line2", null};
        final String[] line3Range = {"line3", null};
        final String[] line4Range = {"line4", null};
        final String[] line5Range = {"line5", null};
        final String[] line6Range = {"line6", null};
        final String[] postCodeRange = {"EC4V 3DB", null};
        final String[] countryCodeRange = {null, "ABC", ""};

        for (final String line1 : line1Range) {
            for (final String countryCode : countryCodeRange) {
                for (final String line2 : line2Range) {
                    for (final String line3 : line3Range) {
                        for (final String line4 : line4Range) {
                            for (final String line5 : line5Range) {
                                for (final String line6 : line6Range) {
                                    for (final String postCode : postCodeRange) {
                                        final String scenario = generateScenario(line1, countryCode, line2, line3, line4, line5, line6, postCode);
                                        fileWriter.print(scenario);
                                    }
                                }
                            }
                        }
                    }
                }
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
            final AddressFeatureFileGenerator generator = new AddressFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + AddressFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
