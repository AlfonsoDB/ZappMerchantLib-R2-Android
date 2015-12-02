package com.zapp.core.generator;

import com.zapp.core.DeliveryAddressType;
import com.zapp.core.ParameterStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Feature file generator for Delivery Address model object.
 *
 * @author msagi
 */
public class DeliveryAddressFeatureFileGenerator extends AbstractFeatureFileGenerator {

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("type | addresseUser | address | identifier |");
    }

    /**
     * Get scenario type.
     *
     * @param identifier          The identifier.
     * @param deliveryAddressType The delivery address type.
     * @param addresseeUser       The addressee user.
     * @param address             The address to deliver.
     * @return The type of the generated scenario.
     */

    public ScenarioType getScenarioType(final String identifier, final DeliveryAddressType deliveryAddressType, final ParameterStatus addresseeUser,
                                        final ParameterStatus address) {

        if (deliveryAddressType == null) {
            return getNegativeScenarioType(getFingerprint(deliveryAddressType));
        }

        if (!isOptional(addresseeUser)) {
            return getNegativeScenarioType(getFingerprint(deliveryAddressType, addresseeUser));
        }

        if (!isOptional(address)) {
            return getNegativeScenarioType(getFingerprint(deliveryAddressType, addresseeUser, address));
        }

        addPositiveScenario(getFingerprint(deliveryAddressType, addresseeUser, address, identifier));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate scenario text.
     *
     * @param identifier          The identifier.
     * @param deliveryAddressType The delivery address type.
     * @param addresseeUser       The addressee user.
     * @param address             The address to deliver.
     * @return The text of the generated scenario.
     */
    private String generateScenario(final String identifier, final DeliveryAddressType deliveryAddressType, final ParameterStatus addresseeUser,
                                    final ParameterStatus address) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(identifier, deliveryAddressType, addresseeUser, address);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("DeliveryAddress.P%d", getNextPositiveScenarioId());
                expectedResultString = "the Delivery Address is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("DeliveryAddress.N%d", getNextNegativeScenarioId());
                expectedResultString = "the Delivery Address is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (identifier != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Identifier is \"%s\"\n", identifier));
        }
        if (deliveryAddressType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Delivery Address Type is \"%s\"\n", deliveryAddressType));
        }
        if (addresseeUser != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Addressee User is \"%s\"\n", addresseeUser));
        }
        if (address != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Address to deliver is \"%s\"\n", address));
        }

        stringBuilder.append("    When I try to create the Delivery Address\n");

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

        fileWriter.println("Feature: Delivery Address\n");

        final String[] identifierRange = {null, "identifier"};
        final LinkedList<DeliveryAddressType> deliveryAddressTypeRange = new LinkedList<DeliveryAddressType>(Arrays.asList(DeliveryAddressType.values()));
        deliveryAddressTypeRange.add(null);

        final LinkedList<ParameterStatus> addressRange = new LinkedList<ParameterStatus>();
        addressRange.add(ParameterStatus.valid);
        addressRange.add(ParameterStatus.invalid);

        for (final DeliveryAddressType deliveryType : deliveryAddressTypeRange) {
            for (final ParameterStatus addresseeUser : ParameterStatus.values()) {
                for (final ParameterStatus address : addressRange) {
                    for (final String identifier : identifierRange) {
                        final String scenario = generateScenario(identifier, deliveryType, addresseeUser, address);
                        fileWriter.print(scenario);
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
        if (args.length != 1) {
            System.err.println("Usage: java " + DeliveryAddressFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
            System.exit(-1);
        }
        try {
            final DeliveryAddressFeatureFileGenerator generator = new DeliveryAddressFeatureFileGenerator();
            generator.generate(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
