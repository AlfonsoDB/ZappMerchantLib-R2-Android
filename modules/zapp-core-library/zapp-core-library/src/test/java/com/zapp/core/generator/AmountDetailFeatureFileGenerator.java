package com.zapp.core.generator;

import com.zapp.core.AmountType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Feature file generator for Amount Detail model object.
 *
 * @author nsevciuc
 */
public class AmountDetailFeatureFileGenerator extends AbstractFeatureFileGenerator {

    /**
     * List of  amount statuses for this generator.
     */
    public enum AmountStatus {
        is_null,
        negative,
        zero,
        positive
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param amountType The amount type.
     * @param price      The price.
     * @param rate       The the rate.
     * @return The text of the test scenario.
     */
    private ScenarioType getScenarioType(final AmountType amountType, final AmountStatus price, final AmountStatus rate) {
        //Requires amount type
        if (isNull(amountType)) {
            return getNegativeScenarioType(getFingerprint(amountType));
        }
        //Requires valid price
        if (!(price == AmountStatus.zero || price == AmountStatus.positive)) {
            return getNegativeScenarioType(getFingerprint(amountType, price));
        }
        if (rate == AmountStatus.negative) {
            return getNegativeScenarioType(getFingerprint(amountType, price, rate));
        } else {
            return getPositiveScenarioType(getFingerprint(amountType, price, rate));
        }
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param amountType The amount type.
     * @param price      The price.
     * @param rate       The the rate.
     * @return The text of the test scenario.
     */
    private String generateScenario(final AmountType amountType, final AmountStatus price, final AmountStatus rate) {
        final ScenarioType scenarioType = getScenarioType(amountType, price, rate);
        final StringBuilder stringBuilder = new StringBuilder();
        final String scenarioId;
        final String expectedResultString;

        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("AmountDetail.P%d", getNextPositiveScenarioId());
                expectedResultString = "Amount Detail is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("AmountDetail.N%d", getNextNegativeScenarioId());
                expectedResultString = "Amount Detail is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }

        stringBuilder.append("  Scenario: ").append(scenarioId).append(": ").append('\n');

        if (!isNull(amountType)) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Amount Type is \"%s\"\n", amountType.name()));
        }
        if (!isNull(price)) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Price is \"%s\"\n", price.name()));
        }
        if (!isNull(rate)) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Rate is \"%s\"\n", rate.name()));
        }

        stringBuilder.append("    When I try to create the Amount Detail\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("type | price | rate");
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

        fileWriter.println("Feature: Amount Detail\n");

        final List<AmountType> amountTypesList = new LinkedList<AmountType>(Arrays.asList(AmountType.values()));
        amountTypesList.add(0, null);

        final List<AmountStatus> priceList = new LinkedList<AmountStatus>(Arrays.asList(AmountStatus.values()));

        final List<AmountStatus> rateList = new LinkedList<AmountStatus>(Arrays.asList(AmountStatus.values()));

        for (AmountType amountType : amountTypesList) {
            for (AmountStatus price : priceList) {
                for (AmountStatus rate : rateList) {
                    final String scenario = generateScenario(amountType, price, rate);
                    fileWriter.print(scenario);
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
            final AmountDetailFeatureFileGenerator generator = new AmountDetailFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + AmountDetailFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
