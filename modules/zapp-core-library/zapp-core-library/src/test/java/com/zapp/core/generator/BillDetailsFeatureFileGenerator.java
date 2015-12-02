package com.zapp.core.generator;

import com.zapp.core.ParameterStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Feature file generator for Bill Details model object.
 *
 * @author msagi
 */
public class BillDetailsFeatureFileGenerator extends AbstractFeatureFileGenerator {

    /**
     * Date formatter.
     */
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param reference  The reference.
     * @param periodFrom The period from value.
     * @param periodTo   The period to value.
     * @param accountId  The account id.
     * @return The text of the test scenario.
     */
    public ScenarioType getScenarioType(final String reference, final String periodFrom, final String periodTo, final String accountId) {

        if (!isRequired(reference)) {
            return getNegativeScenarioType(getFingerprint(reference));
        }

        try {
            if (isRequired(periodFrom) && isRequired(periodTo)) {
                final Date periodFromDate = DATE_FORMAT.parse(periodFrom);
                final Date periodToDate = DATE_FORMAT.parse(periodTo);
                if (periodFromDate.getTime() > periodToDate.getTime()) {
                    return getNegativeScenarioType(getFingerprint(reference, periodFrom, periodTo, accountId));
                }

            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("periodFrom and periodTo must be valid dates formatted as " + DATE_FORMAT);
        }

        addPositiveScenario(getFingerprint(reference, periodFrom, periodTo, accountId));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param reference  The reference.
     * @param periodFrom The period from value.
     * @param periodTo   The period to value.
     * @param accountId  The account id.
     * @return The text of the test scenario.
     */
    public String generateScenario(final String reference, final String periodFrom, final String periodTo, final String accountId) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(reference, periodFrom, periodTo, accountId);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("BillDetails.P%d", getNextPositiveScenarioId());
                expectedResultString = "The Bill Details is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("BillDetails.N%d", getNextNegativeScenarioId());
                expectedResultString = "The Bill Details is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (reference != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Reference is \"%s\"\n", reference));
        }
        if (periodFrom != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Period From is \"%s\"\n", periodFrom));
        }
        if (periodTo != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Period To is \"%s\"\n", periodTo));
        }
        if (accountId != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Account Id is \"%s\"\n", accountId));
        }

        stringBuilder.append("    When I try to create the Bill Details\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("reference | periodFrom | periodTo | accountId");
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

        fileWriter.println("Feature: Bill Details\n");

        final String[] accountIdRange = {null, "", "12345678"};
        final String[] referenceRange = {null, "", "ref1234abcd"};
        final String[] periodFromRange = {null, "2015-01-01", "2015-12-31"};
        final String[] periodToRange = {null, "2015-12-31", "2015-01-01"};

        for (final String reference : referenceRange) {
            for (final String periodFrom : periodFromRange) {
                for (final String periodTo : periodToRange) {
                    for (final String accountId : accountIdRange) {
                        final String scenario = generateScenario(reference, periodFrom, periodTo, accountId);
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
        try {
            final BillDetailsFeatureFileGenerator generator = new BillDetailsFeatureFileGenerator();

            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + BillDetailsFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
