package com.zapp.core.generator;

import com.zapp.core.ParameterStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Feature file generator for Bank Account model object.
 *
 * @author msagi
 */
public class BankAccountFeatureFileGenerator extends AbstractFeatureFileGenerator {

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param number         The number.
     * @param name           The name.
     * @param sortCode       The sort code.
     * @param balance        The balance.
     * @param availableFunds The available funds.
     * @return The text of the test scenario.
     */
    public ScenarioType getScenarioType(final String number, final String name, final String sortCode, final ParameterStatus balance,
                                        final ParameterStatus availableFunds) {

        if (!isRequired(number)) {
            return getNegativeScenarioType(getFingerprint(number));
        }

        if (!isRequired(name)) {
            return getNegativeScenarioType(getFingerprint(number, name));
        }

        if (!isRequired(sortCode)) {
            return getNegativeScenarioType(getFingerprint(number, name, sortCode));
        }

        if (!isRequired(balance)) {
            return getNegativeScenarioType(getFingerprint(number, name, sortCode, balance));
        }

        if (!isRequired(availableFunds)) {
            return getNegativeScenarioType(getFingerprint(number, name, sortCode, balance, availableFunds));
        }

        addPositiveScenario(getFingerprint(number, name, sortCode, balance, availableFunds));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param number         The number.
     * @param name           The name.
     * @param sortCode       The sort code.
     * @param balance        The balance.
     * @param availableFunds The available funds.
     * @return The text of the test scenario.
     */
    public String generateScenario(final String number, final String name, final String sortCode, final ParameterStatus balance, final ParameterStatus availableFunds) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(number, name, sortCode, balance, availableFunds);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("BankAccount.P%d", getNextPositiveScenarioId());
                expectedResultString = "Bank Account is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("BankAccount.N%d", getNextNegativeScenarioId());
                expectedResultString = "The Bank Account is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (number != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Number is \"%s\"\n", number));
        }
        if (name != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Name is \"%s\"\n", name));
        }
        if (sortCode != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Sort Code is \"%s\"\n", sortCode));
        }
        if (balance != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Balance is \"%s\"\n", balance.name()));
        }
        if (availableFunds != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Available Funds are \"%s\"\n", availableFunds.name()));
        }

        stringBuilder.append("    When I try to create the Bank Account\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("number | name | sortCode | balance | availableFunds");
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

        fileWriter.println("Feature: Bank Account\n");

        final String[] numberRange = {null, "", "12345678"};
        final String[] nameRange = {null, "", "Olivia Kensington"};
        final String[] sortCodeRange = {null, "", "12-12-12"};

        final List<ParameterStatus> balanceStatusList = new LinkedList<ParameterStatus>(Arrays.asList(
                ParameterStatus.values()));

        final List<ParameterStatus> availableFundsStatusList = new LinkedList<ParameterStatus>(Arrays.asList(
                ParameterStatus.values()));

        for (final String number : numberRange) {
            for (final String name : nameRange) {
                for (final String sortCode : sortCodeRange) {
                    for (final ParameterStatus balance : balanceStatusList) {
                        for (final ParameterStatus availableFunds : availableFundsStatusList) {
                            final String scenario = generateScenario(number, name, sortCode, balance, availableFunds);
                            fileWriter.print(scenario);
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
        if (args.length != 1) {
            System.err.println("Usage: java " + BankAccountFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
            System.exit(-1);
        }
        try {
            final BankAccountFeatureFileGenerator generator = new BankAccountFeatureFileGenerator();
            generator.generate(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
