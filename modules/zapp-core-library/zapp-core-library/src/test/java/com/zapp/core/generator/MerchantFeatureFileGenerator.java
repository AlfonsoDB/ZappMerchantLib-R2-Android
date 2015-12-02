package com.zapp.core.generator;

import com.zapp.core.ParameterStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Feature file generator for Merchant model object.
 *
 * @author msagi
 */
public class MerchantFeatureFileGenerator extends AbstractFeatureFileGenerator {

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("identifier | name | email | address | phone | website |");
    }

    /**
     * Get scenario type.
     *
     * @param identifier The merchant identifier.
     * @param name       The merchant name.
     * @param email      The merchant email.
     * @param phone      The merchant phone.
     * @param address    The merchant address.
     * @param website    The merchant website.
     * @return The type of the generated scenario.
     */

    public ScenarioType getScenarioType(final String identifier, final EmailTestValues email, final String phone, final ParameterStatus address, final String website,
            final String name) {

        if (!isRequired(identifier)) {
            return getNegativeScenarioType(getFingerprint(identifier));
        }

        switch (email) {
            case EMPTY:
            case INVALID:
                return getNegativeScenarioType(getFingerprint(identifier, email.getValue()));
            case NULL:
            case VALID:
                //positive scenario is added later
                break;
        }
        final String emailValue = email == null ? null : email.getValue();

        if (!isOptional(address)) {
            return getNegativeScenarioType(getFingerprint(identifier, emailValue, address));
        }

        //phone is optional
        //website is optional
        //name is optional

        addPositiveScenario(getFingerprint(identifier, emailValue, address, phone, website, name));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate scenario text.
     *
     * @param identifier The merchant identifier.
     * @param name       The merchant name.
     * @param email      The merchant email.
     * @param phone      The merchant phone.
     * @param address    The merchant address.
     * @param website    The merchant website.
     * @return The text of the generated scenario.
     */
    private String generateScenario(final String identifier, final EmailTestValues email, final String phone, final ParameterStatus address, final String website,
            final String name) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(identifier, email, phone, address, website, name);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("Merchant.P%d", getNextPositiveScenarioId());
                expectedResultString = "the Merchant is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("Merchant.N%d", getNextNegativeScenarioId());
                expectedResultString = "the Merchant is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (identifier != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant Identifier is \"%s\"\n", identifier));
        }
        if (email != EmailTestValues.NULL) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant Email is \"%s\"\n", email.getValue()));
        }
        if (phone != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant Phone is \"%s\"\n", phone));
        }
        if (address != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant Address is \"%s\"\n", address));
        }
        if (website != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant Website is \"%s\"\n", website));
        }
        if (name != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant Name is \"%s\"\n", name));
        }

        stringBuilder.append("    When I try to create the Merchant\n");

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

        fileWriter.println("Feature: Merchant\n");

        final String[] identifierRange = {null, "", "identifier"};
        final String[] phoneRange = {null, "12345678"};
        final String[] websiteRange = {null, "website"};
        final String[] nameRange = {null, "merchantName"};

        for (final String identifier : identifierRange) {
            for (final EmailTestValues email : EmailTestValues.values()) {
                for (final ParameterStatus address : ParameterStatus.values()) {
                    for (final String phone : phoneRange) {
                        for (final String name : nameRange) {
                            for (final String website : websiteRange) {
                                final String scenario = generateScenario(identifier, email, phone, address, website, name);
                                fileWriter.print(scenario);
                            }
                        }
                    }
                }
            }
        }

        fileWriter.close();

        dumpScenarioFingerprints();

        dumpGenerationStatistics();
    }

    /**
     * Command line main method.
     *
     * @param args The list of command line arguments.
     */
    public static void main(final String[] args) {
        try {
            final MerchantFeatureFileGenerator generator = new MerchantFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + MerchantFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
