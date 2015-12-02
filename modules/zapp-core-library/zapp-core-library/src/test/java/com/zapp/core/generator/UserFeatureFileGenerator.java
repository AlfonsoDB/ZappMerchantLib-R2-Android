package com.zapp.core.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Feature file generator for User model object.
 *
 * @author msagi
 */
public class UserFeatureFileGenerator extends AbstractFeatureFileGenerator {

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("firstName | email | title | middleName | lastName | phone |");
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param firstName  The user's first name.
     * @param email      The user's email address.
     * @param title      The user's title.
     * @param middleName The user's middle name.
     * @param lastName   The user's last name.
     * @param phone      The user's phone.
     * @return The text of the test scenario.
     */
    public ScenarioType getScenarioType(final String firstName, final EmailTestValues email, final String title, final String middleName, final String lastName,
            final String phone) {

        final String emailValue = email == null ? null : email.getValue();
        if (!isRequired(firstName) && !isRequired(emailValue)) {
            return getNegativeScenarioType(getFingerprint(firstName, emailValue));
        }

        switch (email) {
            case EMPTY:
            case INVALID:
                return getNegativeScenarioType(getFingerprint(firstName, emailValue));
            case NULL:
            case VALID:
                //positive scenario is added later
                break;
        }

        addPositiveScenario(getFingerprint(firstName, emailValue, title, middleName, lastName, phone));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate scenario text.
     *
     * @param firstName  The user's first name.
     * @param email      The user's email address.
     * @param title      The user's title.
     * @param middleName The user's middle name.
     * @param lastName   The user's last name.
     * @param phone      The user's phone.
     * @return The text of the generated scenario.
     */
    private String generateScenario(final String firstName, final EmailTestValues email, final String title, final String middleName, final String lastName,
            final String phone) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(firstName, email, title, middleName, lastName, phone);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("User.P%d", getNextPositiveScenarioId());
                expectedResultString = "the User is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("User.N%d", getNextNegativeScenarioId());
                expectedResultString = "the User is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (firstName != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the user's First Name is \"%s\"\n", firstName));
        }
        if (title != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the user's Title is \"%s\"\n", title));
        }
        if (middleName != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the user's Middle Name is \"%s\"\n", middleName));
        }
        if (lastName != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the user's Last Name is \"%s\"\n", lastName));
        }
        if (email != EmailTestValues.NULL) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the user's Email Address is \"%s\"\n", email.getValue()));
        }
        if (phone != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the user's Phone Number is \"%s\"\n", phone));
        }

        stringBuilder.append("    When I try to create the User\n");

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

        fileWriter.println("Feature: User\n");

        final String[] firstNameRange = {null, "", "John"};
        final String[] lastNameRange = {null, "", "Doe"};
        final String[] middleNameRange = {null, "", "Thomas"};
        final String[] titleRange = {null, "", "Mr"};
        final String[] phoneRange = {null, "", "7766554433"};

        for (final String firstName : firstNameRange) {
            for (final EmailTestValues email : EmailTestValues.values()) {
                for (final String title : titleRange) {
                    for (final String middleName : middleNameRange) {
                        for (final String lastName : lastNameRange) {
                            for (final String phone : phoneRange) {
                                final String scenario = generateScenario(firstName, email, title, middleName, lastName, phone);
                                fileWriter.print(scenario);
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
        if (args.length != 1) {
            System.err.println("Usage: java " + UserFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
            System.exit(-1);
        }
        try {
            final UserFeatureFileGenerator generator = new UserFeatureFileGenerator();
            generator.generate(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
