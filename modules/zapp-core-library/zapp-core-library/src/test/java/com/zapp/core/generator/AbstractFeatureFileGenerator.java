package com.zapp.core.generator;

import com.zapp.core.ParameterStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract base class for .feature file generators.
 *
 * @author msagi
 */
public abstract class AbstractFeatureFileGenerator {

    /**
     * List of test email values.
     */
    protected enum EmailTestValues {
        NULL(null),
        EMPTY(""),
        INVALID("@a.com"),
        VALID("hi@merchant.co.uk"),;

        private final String value;

        EmailTestValues(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * The index of the precondition. Used to choose whether to have @Given or @And in the scenario.
     */
    private int preconditionIndex = 0;

    /**
     * The index of the positive scenario.
     */
    private int positiveScenarioIndex = 0;

    /**
     * The index of the negative scenario.
     */
    private int negativeScenarioIndex = 0;

    /**
     * Fingerprints of the negative scenarios. Used to collect minimum set of negative scenarios.
     */
    private final List<String> negativeScenarioFingerprints = new LinkedList<String>();

    /**
     * Fingerprints of the positive scenarios. Used to collect positive scenarios.
     */
    private final List<String> positiveScenarioFingerprints = new LinkedList<String>();

    /**
     * Fingerprints of all the scenarios. Used to dump scenario fingerprint list.
     */
    private final List<String> scenarioFingerprints = new LinkedList<String>();

    /**
     * Reset the precondition tagging.
     */
    protected void resetPreconditionTagging() {
        preconditionIndex = 0;
    }

    /**
     * Get the next padded Gherkin style precondition tag.
     *
     * @return "Given" if precondition index is 0, "And" otherwise.
     */
    protected String getNextPreconditionTag() {
        if (preconditionIndex++ == 0) {
            return "    Given ";
        }
        return "    And ";
    }

    /**
     * Convenience method to test status value.
     *
     * @param o The Object to test.
     * @return True if the Object is null, false otherwise.
     */
    protected static boolean isNull(final Object o) {
        return o == null;
    }

    /**
     * Convenience method to test status value.
     *
     * @param status The status to test.
     * @return True if the status is null, false otherwise.
     */
    protected static boolean isNull(final ParameterStatus status) {
        return status == ParameterStatus.is_null;
    }

    /**
     * Convenience method to test status value.
     *
     * @param status The status to test.
     * @return True if the status is valid, false otherwise.
     */
    protected static boolean isRequired(final ParameterStatus status) {
        return status == ParameterStatus.valid;
    }

    /**
     * Convenience method to test string value.
     *
     * @param string The string to test.
     * @return True if the string is not null or empty.
     */
    protected static boolean isRequired(final String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * Convenience method to test status value.
     *
     * @param status The status to test.
     * @return True if status is null or valid, false otherwise.
     */
    protected static boolean isOptional(final ParameterStatus status) {
        return status == ParameterStatus.valid || status == ParameterStatus.is_null;
    }

    /**
     * Get the next positive scenario id.
     *
     * @return The next id.
     */
    protected int getNextPositiveScenarioId() {
        return positiveScenarioIndex++;
    }

    /**
     * Get the next negative scenario id.
     *
     * @return The next id.
     */
    protected int getNextNegativeScenarioId() {
        return negativeScenarioIndex++;
    }

    /**
     * Get the negative scenario type based on given fingerprint.
     *
     * @param fingerprint The negative scenario fingerprint.
     * @return The scenario type.
     */
    protected ScenarioType getNegativeScenarioType(final String fingerprint) {
        if (negativeScenarioFingerprints.contains(fingerprint)) {
            return ScenarioType.DUPLICATE_NEGATIVE;
        }
        negativeScenarioFingerprints.add(fingerprint);
        scenarioFingerprints.add(fingerprint);
        return ScenarioType.NEGATIVE;
    }

    /**
     * Get the positive scenario type based on given fingerprint.
     *
     * @param fingerprint The positive scenario fingerprint.
     * @return The scenario type.
     */
    protected ScenarioType getPositiveScenarioType(final String fingerprint) {
        addPositiveScenario(fingerprint);
        return ScenarioType.POSITIVE;
    }

    /**
     * Record positive scenario.
     *
     * @param fingerprint The fingerprint of the scenario.
     */
    protected void addPositiveScenario(final String fingerprint) {
        positiveScenarioFingerprints.add(fingerprint);
        scenarioFingerprints.add(fingerprint);
    }

    /**
     * Create a fingerprint from given object list.
     *
     * @param objects The list of objects to create fingerprint for.
     * @return The fingerprint {@link String}
     */
    protected static String getFingerprint(final Object... objects) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Object object : objects) {
            if (object == null) {
                stringBuilder.append("null | ");
            } else {
                stringBuilder.append('"').append(object).append("\" | ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Dump scenario fingerprint list.
     */
    protected void dumpScenarioFingerprints() {
        for (final String fingerprint : scenarioFingerprints) {
            if (positiveScenarioFingerprints.contains(fingerprint)) {
                System.out.println(fingerprint + "  (+)");
            } else {
                System.out.println(fingerprint);
            }
        }
    }

    protected void dumpGenerationStatistics() {
        System.out.println(String.format("%d test scenarios generated (%d positive, %d negative)", (positiveScenarioIndex + negativeScenarioIndex), positiveScenarioIndex,
                negativeScenarioIndex));
    }

    /**
     * Generate default feature file for class.
     *
     * @throws FileNotFoundException If the default feature file path is invalid.
     */
    protected void autoGenerateFeatureFile() throws FileNotFoundException {
        String featureFileName = getClass().getSimpleName();
        final int postfixIndex = featureFileName.indexOf("FeatureFileGenerator");
        if (postfixIndex != -1) {
            featureFileName = featureFileName.substring(0, postfixIndex);
        }
        featureFileName = featureFileName.replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2").toLowerCase();

        final String featureFilePath = "." + File.separator + "zapp-core-library" + File.separator + "src" + File.separator + "test" + File.separator + "resources" +
                File.separator + "features" + File.separator + featureFileName + "_generated.feature";
        generate(featureFilePath);
    }

    /**
     * Generate BDD test .feature file content
     *
     * @param bddTestFilePath The output file path to generate content into.
     * @throws FileNotFoundException If given path is invalid.
     */
    public void generate(final String bddTestFilePath) throws FileNotFoundException {
        final File outFile = new File(bddTestFilePath);
        if (outFile.isFile()) {
            System.err.println("Warning: BDD test output file will be overwritten: " + outFile.getPath());
            outFile.delete();
        }
        generate(outFile);
    }

    /**
     * Dump the header of the scenario fingerprints.
     */
    public abstract void dumpScenarioFingerprintsHeader();

    /**
     * Generate BDD test .feature file content.
     *
     * @param bddTestFile The output file to generate content into.
     * @throws FileNotFoundException If given file is invalid.
     */
    public abstract void generate(final File bddTestFile) throws FileNotFoundException;
}