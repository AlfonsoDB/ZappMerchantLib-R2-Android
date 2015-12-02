package com.zapp.core.generator;

import com.zapp.core.CheckoutType;
import com.zapp.core.DeliveryType;
import com.zapp.core.ParameterStatus;
import com.zapp.core.TransactionStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Feature file generator for Payment Auth model object.
 *
 * @author msagi
 */
public class PaymentAuthFeatureFileGenerator extends AbstractFeatureFileGenerator {

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param transactionStatus         The transaction status.
     * @param checkoutType              The checkout type.
     * @param deliveryType              The delivery type.
     * @param bankAccountStatus         The bank account object status.
     * @param userStatus                The user object status.
     * @param finalCurrencyAmountStatus The final amount object status.
     * @param deliveryAddressStatus     The delivery address object status.
     * @param payconnectEnabled         The payconnect indicator flag.
     * @return The expected result of the test scenario.
     */
    public ScenarioType getScenarioType(final TransactionStatus transactionStatus, final CheckoutType checkoutType,
            final DeliveryType deliveryType, final ParameterStatus finalCurrencyAmountStatus, final ParameterStatus userStatus,
            final ParameterStatus deliveryAddressStatus, final ParameterStatus bankAccountStatus, final Boolean payconnectEnabled) {

        resetPreconditionTagging();

        if (transactionStatus == null) {
            return getNegativeScenarioType(getFingerprint(transactionStatus));
        }

        if (transactionStatus != TransactionStatus.DECLINED && transactionStatus != TransactionStatus.PAYMENT_ENQUIRY_FAILED) {
            if (checkoutType == null) {
                return getNegativeScenarioType(getFingerprint(transactionStatus, checkoutType));
            }

            if (deliveryType == null) {
                return getNegativeScenarioType(getFingerprint(transactionStatus, checkoutType, deliveryType));
            }

            if (checkoutType == CheckoutType.QUICK) {
                if (deliveryType == DeliveryType.ADDRESS) {
                    if (!isRequired(deliveryAddressStatus)) {
                        return getNegativeScenarioType(
                                getFingerprint(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus, userStatus,
                                        deliveryAddressStatus));
                    }
                }
            }
        }

        if (!isOptional(finalCurrencyAmountStatus)) {
            return getNegativeScenarioType(
                    getFingerprint(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus));
        }

        if (!isOptional(userStatus)) {
            return getNegativeScenarioType(
                    getFingerprint(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus, userStatus));
        }

        if (!isOptional(deliveryAddressStatus)) {
            return getNegativeScenarioType(
                    getFingerprint(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus, userStatus, deliveryAddressStatus));
        }

        if (!isOptional(bankAccountStatus)) {
            return getNegativeScenarioType(
                    getFingerprint(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus, userStatus, deliveryAddressStatus,
                            bankAccountStatus));
        }

        addPositiveScenario(
                getFingerprint(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus, userStatus, deliveryAddressStatus,
                        bankAccountStatus, payconnectEnabled));
        return ScenarioType.POSITIVE;
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param transactionStatus         The transaction status.
     * @param checkoutType              The checkout type.
     * @param deliveryType              The delivery type.
     * @param bankAccountStatus         The bank account object status.
     * @param userStatus                The user object status.
     * @param finalCurrencyAmountStatus The final amount object status.
     * @param deliveryAddressStatus     The delivery address object status.
     * @param payconnectEnabled         The payconnect indicator flag.
     * @return The text of the test scenario.
     */
    public String generateScenario(final TransactionStatus transactionStatus, final CheckoutType checkoutType,
            final DeliveryType deliveryType, final ParameterStatus finalCurrencyAmountStatus, final ParameterStatus userStatus,
            final ParameterStatus deliveryAddressStatus, final ParameterStatus bankAccountStatus, final Boolean payconnectEnabled) {

        final StringBuilder stringBuilder = new StringBuilder();

        final ScenarioType scenarioType = getScenarioType(transactionStatus, checkoutType, deliveryType, finalCurrencyAmountStatus, userStatus,
                deliveryAddressStatus, bankAccountStatus, payconnectEnabled);

        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("PaymentAuth.P%d", getNextPositiveScenarioId());
                expectedResultString = "the Payment Auth is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("PaymentRequest.N%d", getNextNegativeScenarioId());
                expectedResultString = "the Payment Auth is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append('\n');
        if (transactionStatus != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Transaction Status for payment auth is \"%s\"\n", transactionStatus.name()));
        }
        if (checkoutType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Checkout Type for payment auth is \"%s\"\n", checkoutType.name()));
        }
        if (deliveryType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Delivery Type for payment auth is \"%s\"\n", deliveryType.name()));
        }
        if (bankAccountStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Bank Account for payment auth is \"%s\"\n", bankAccountStatus.name()));
        }
        if (userStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the User for payment auth is \"%s\"\n", userStatus.name()));
        }
        if (finalCurrencyAmountStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Final Amount for payment auth is \"%s\"\n", finalCurrencyAmountStatus.name()));
        }
        if (deliveryAddressStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Delivery Address for payment auth is \"%s\"\n", deliveryAddressStatus.name()));
        }
        if (payconnectEnabled != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Transaction Payconnect Enabled is \"%s\"\n", payconnectEnabled));
        }

        stringBuilder.append("    When I try to create the Payment Auth\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println(
                "transactionStatus | checkoutType | deliveryType | paymentStatus | finalAmount | bankAccount | user | deliveryAddress | bankAccount | payconnectEnabled");
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

        fileWriter.println("Feature: Payment Auth\n");

        final List<TransactionStatus> transactionStatusList = new LinkedList<TransactionStatus>(Arrays.asList(TransactionStatus.values()));
        transactionStatusList.add(0, null);

        final Boolean[] payconnectEnabledList = {null, false, true};

        final List<CheckoutType> checkoutTypeList = new LinkedList<CheckoutType>(Arrays.asList(CheckoutType.values()));
        checkoutTypeList.add(0, null);

        final List<DeliveryType> deliveryTypeList = new LinkedList<DeliveryType>(Arrays.asList(DeliveryType.values()));
        deliveryTypeList.add(0, null);

        final List<ParameterStatus> bankAccountStatusList = new LinkedList<ParameterStatus>(Arrays.asList(ParameterStatus.values()));

        final List<ParameterStatus> userStatusList = new LinkedList<ParameterStatus>(Arrays.asList(
                ParameterStatus.values()));

        final List<ParameterStatus> currencyAmountStatusList = new LinkedList<ParameterStatus>(Arrays.asList(
                ParameterStatus.values()));

        final List<ParameterStatus> deliveryAddressStatusList = new LinkedList<ParameterStatus>(Arrays.asList(
                ParameterStatus.values()));

        for (final TransactionStatus transactionStatus : transactionStatusList) {
            for (final CheckoutType checkoutType : checkoutTypeList) {
                for (final DeliveryType deliveryType : deliveryTypeList) {
                    for (final ParameterStatus finalCurrencyAmountStatus : currencyAmountStatusList) {
                        for (final ParameterStatus userStatus : userStatusList) {
                            for (final ParameterStatus deliveryAddressStatus : deliveryAddressStatusList) {
                                for (final ParameterStatus bankAccountStatus : bankAccountStatusList) {
                                    for (final Boolean payconnectEnabled : payconnectEnabledList) {
                                        final String scenario = generateScenario(transactionStatus, checkoutType, deliveryType,
                                                finalCurrencyAmountStatus, userStatus, deliveryAddressStatus, bankAccountStatus, payconnectEnabled);
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
            final PaymentAuthFeatureFileGenerator generator = new PaymentAuthFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + PaymentAuthFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
