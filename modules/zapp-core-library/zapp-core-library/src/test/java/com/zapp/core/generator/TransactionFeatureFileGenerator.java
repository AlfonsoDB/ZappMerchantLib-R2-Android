package com.zapp.core.generator;

import com.zapp.core.ParameterStatus;
import com.zapp.core.RetrievalMethod;
import com.zapp.core.TransactionStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Feature file generator for Transaction model object.
 *
 * @author msagi
 */
public class TransactionFeatureFileGenerator extends AbstractFeatureFileGenerator {

    /**
     * List of payment requests statuses for this generator.
     */
    public enum PaymentRequestStatus {
        is_null,
        is_immediate,
        is_deferred
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param retrievalMethod            The retrieval method.
     * @param retrievalExpiryInterval    The retrieval expiry interval.
     * @param notificationSentStatus     The notification sent indicator.
     * @param paymentRequestStatus       The payment request object status.
     * @param settlementRetrievalId      The settlement retrieval id.
     * @param transactionIdStatus        The transaction id object status.
     * @param paymentAuthStatus          The payment auth object status.
     * @param brn                        The BRN (Zapp code).
     * @param confirmationExpiryInterval The confirmation expiry interval.
     * @param askForPayconnect           The ask for payconnect indicator.
     * @return The text of the test scenario.
     */
    public ScenarioType getScenarioType(final RetrievalMethod retrievalMethod, final Integer retrievalExpiryInterval, final Boolean notificationSentStatus,
            final PaymentRequestStatus paymentRequestStatus, final String settlementRetrievalId, final ParameterStatus transactionIdStatus,
            final ParameterStatus paymentAuthStatus, final String brn, final Integer confirmationExpiryInterval, final Boolean askForPayconnect) {
        // Required fields
        if (retrievalMethod == null) {
            return getNegativeScenarioType(getFingerprint(retrievalMethod));
        }
        if (retrievalExpiryInterval == null) {
            return getNegativeScenarioType(getFingerprint(retrievalMethod, retrievalExpiryInterval));
        }
        if (notificationSentStatus == null) {
            return getNegativeScenarioType(getFingerprint(retrievalMethod, retrievalExpiryInterval, notificationSentStatus));
        }
        // settlementRetrievalId is required for immediate payment request type
        if (paymentRequestStatus == PaymentRequestStatus.is_immediate && !isRequired(settlementRetrievalId)) {
            return getNegativeScenarioType(getFingerprint(retrievalMethod, retrievalExpiryInterval, notificationSentStatus, transactionIdStatus, paymentRequestStatus,
                    settlementRetrievalId));
        }
        if (!isOptional(transactionIdStatus)) {
            return getNegativeScenarioType(getFingerprint(retrievalMethod, retrievalExpiryInterval, notificationSentStatus, paymentRequestStatus,
                    settlementRetrievalId, transactionIdStatus));
        }
        if (!isOptional(paymentAuthStatus)) {
            return getNegativeScenarioType(getFingerprint(retrievalMethod, retrievalExpiryInterval, notificationSentStatus, paymentRequestStatus,
                    settlementRetrievalId, transactionIdStatus, paymentAuthStatus));
        }

        //optional brn
        //optional confirmationExpiryInterval
        //optional askForPayconnect

        //defining positive combination(s)
        addPositiveScenario(getFingerprint(retrievalMethod, retrievalExpiryInterval, notificationSentStatus, paymentRequestStatus,
                settlementRetrievalId, transactionIdStatus, paymentAuthStatus, brn, confirmationExpiryInterval, askForPayconnect));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param retrievalMethod            The retrieval method.
     * @param retrievalExpiryInterval    The retrieval expiry interval.
     * @param notificationSentStatus     The notification sent indicator.
     * @param paymentRequestStatus       The payment request object status.
     * @param settlementRetrievalId      The settlement retrieval id.
     * @param transactionIdStatus        The transaction id object status.
     * @param paymentAuthStatus          The payment auth object status.
     * @param brn                        The BRN (Zapp code).
     * @param confirmationExpiryInterval The confirmation expiry interval.
     * @param askForPayconnect           The ask for payconnect indicator.
     * @return The text of the test scenario.
     */
    public String generateScenario(final RetrievalMethod retrievalMethod, final Integer retrievalExpiryInterval, final Boolean notificationSentStatus,
            final PaymentRequestStatus paymentRequestStatus, final String settlementRetrievalId, final ParameterStatus transactionIdStatus,
            final ParameterStatus paymentAuthStatus, final String brn, final Integer confirmationExpiryInterval, final Boolean askForPayconnect) {

        resetPreconditionTagging();
        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(retrievalMethod, retrievalExpiryInterval, notificationSentStatus,
                paymentRequestStatus, settlementRetrievalId, transactionIdStatus, paymentAuthStatus, brn,
                confirmationExpiryInterval, askForPayconnect);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("Transaction.P%d", getNextPositiveScenarioId());
                expectedResultString = "the Transaction is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("Transaction.N%d", getNextNegativeScenarioId());
                expectedResultString = "the Transaction is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');

        if (retrievalMethod != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Retrieval Method of the transaction is \"%s\"\n", retrievalMethod.name()));
        }
        if (retrievalExpiryInterval != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Retrieval Expiry Interval of the transaction is \"%s\"\n", retrievalExpiryInterval));
        }
        if (notificationSentStatus != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Notification Sent status of the transaction is \"%s\"\n", notificationSentStatus));
        }
        if (paymentRequestStatus != PaymentRequestStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Payment Request of the transaction is \"%s\"\n", paymentRequestStatus.name()));
        }
        if (settlementRetrievalId != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Settlement Retrieval Id of the transaction is \"%s\"\n", settlementRetrievalId));
        }
        if (transactionIdStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Transaction Id of the transaction is \"%s\"\n", transactionIdStatus.name()));
        }

        if (paymentAuthStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Payment Auth of the transaction is \"%s\"\n", paymentAuthStatus.name()));
        }
        if (brn != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Zapp Code of the transaction is \"%s\"\n", brn));
        }
        if (confirmationExpiryInterval != null) {
            stringBuilder.append(getNextPreconditionTag()).append(
                    String.format("the Confirmation Expiry Interval of the transaction is \"%s\"\n", confirmationExpiryInterval));
        }
        if (askForPayconnect != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Ask for PayConnect of the transaction is \"%s\"\n", askForPayconnect));
        }

        stringBuilder.append("    When I try to create the Transaction\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println(
                "retrievalMethod | retrievalExpiryInterval | notificationSent | paymentRequestStatus  |  settlementRetrievalId | transactionIdStatus |  paymentAuthStatus | brn | confirmationExpiryInterval | askForPayconnect");
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

        fileWriter.println("Feature: Transaction\n");

        final List<RetrievalMethod> retrievalMethodList = new LinkedList<RetrievalMethod>(Arrays.asList(RetrievalMethod.values()));
        retrievalMethodList.add(0, null);

        final Integer[] retrievalExpiryIntervalList = {null, -23, 0, 209};

        final Boolean[] notificationSentList = {null, false, true};

        final PaymentRequestStatus[] paymentRequestStatusList = PaymentRequestStatus.values();

        final String[] settlementRetrivalIdsList = {null, "", "id"};

        final List<TransactionStatus> transactionStatusList = new LinkedList<TransactionStatus>(Arrays.asList(TransactionStatus.values()));
        transactionStatusList.add(0, null);

        final ParameterStatus[] transactionIdStatusList = ParameterStatus.values();

        final ParameterStatus[] paymentAuthStatusList = ParameterStatus.values();

        final String[] brnList = {null, "brn"};

        final Integer[] confirmationExpiryIntervalList = {null, -10, 0, 500};

        final Boolean[] askForPayConnectList = {null, false, true};

        for (final RetrievalMethod retrievalMethod : retrievalMethodList) {
            for (final Integer retrievalExpiryInterval : retrievalExpiryIntervalList) {
                for (final Boolean notificationSentStatus : notificationSentList) {
                    for (final PaymentRequestStatus paymentRequestStatus : paymentRequestStatusList) {
                        for (final String settlementRetrievalId : settlementRetrivalIdsList) {
                            for (final ParameterStatus transactionIdStatus : transactionIdStatusList) {
                                for (final ParameterStatus paymentAuthStatus : paymentAuthStatusList) {
                                    for (final String brn : brnList) {
                                        for (final Integer confirmationExpiryInterval : confirmationExpiryIntervalList) {
                                            for (final Boolean askForPayconnect : askForPayConnectList) {
                                                final String scenario = generateScenario(retrievalMethod, retrievalExpiryInterval, notificationSentStatus,
                                                        paymentRequestStatus, settlementRetrievalId, transactionIdStatus, paymentAuthStatus, brn,
                                                        confirmationExpiryInterval, askForPayconnect);
                                                fileWriter.print(scenario);
                                            }
                                        }
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
            final TransactionFeatureFileGenerator generator = new TransactionFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + TransactionFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
