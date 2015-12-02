package com.zapp.core.generator;

import com.zapp.core.ACRType;
import com.zapp.core.CheckoutType;
import com.zapp.core.DeliveryType;
import com.zapp.core.ParameterStatus;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Feature file generator for Payment Request model object.
 *
 * @author msagi
 */
public class PaymentRequestFeatureFileGenerator extends AbstractFeatureFileGenerator {

    /**
     * List of currency amount statuses for this generator.
     */
    public enum CurrencyAmountStatus {
        is_null,
        invalid,
        negative,
        zero,
        positive,
    }

    /**
     * Convenience method to test if delivery type is in the list.
     *
     * @param deliveryType  The delivery type to check.
     * @param deliveryTypes The list of delivery types to check against.
     * @return True if given delivery type is on the given list, false otherwise.
     */
    private static boolean isDeliveryTypeIn(final DeliveryType deliveryType, final DeliveryType... deliveryTypes) {
        for (final DeliveryType deliveryTypesItem : deliveryTypes) {
            if (deliveryType == deliveryTypesItem) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param rtpType        The payment request type.
     * @param paymentType    The payment type.
     * @param checkoutType   The checkout type.
     * @param deliveryType   The delivery type.
     * @param merchantStatus The merchant status.
     * @return The text of the test scenario.
     */
    private String generateScenario(final RTPType rtpType, final DeliveryType deliveryType, final ParameterStatus merchantStatus,
            final PaymentType paymentType, final CheckoutType checkoutType) {

        final ScenarioType scenarioType = getScenarioType(rtpType, deliveryType, merchantStatus, paymentType, checkoutType);
        final StringBuilder stringBuilder = new StringBuilder();
        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("PaymentRequest.P%d", getNextPositiveScenarioId());
                expectedResultString = "Payment Request is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("PaymentRequest.N%d", getNextNegativeScenarioId());
                expectedResultString = "Payment Request is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append(": ").append('\n');

        if (rtpType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the RTP Type is \"%s\"\n", rtpType.name()));
        }
        if (deliveryType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Delivery Type is \"%s\"\n", deliveryType.name()));
        }
        if (merchantStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Merchant is \"%s\"\n", merchantStatus.name()));
        }
        if (paymentType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Payment Type is \"%s\"\n", paymentType.name()));
        }
        if (checkoutType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Checkout Type is \"%s\"\n", checkoutType.name()));
        }

        stringBuilder.append("    When I try to create the Payment Request\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param rtpType                  The payment request type.
     * @param currencyAmountStatus     The amount status.
     * @param acrType                  The authentication check required type.
     * @param defrdRTPExpDateTime      The deferred Request To Pay Expiry Datetime.
     * @param defrdRTPAgrmtAmount      The deferred Request To Pay Agreement Amount.
     * @param defrdRTPMaxAgrdAmount    The deferred Request To Pay Maximum Agreed Amount.
     * @param defrdAmountDetailsStatus The deferred amount details.
     * @return The text of the test scenario.
     */
    private String generateScenario(final RTPType rtpType, final CurrencyAmountStatus currencyAmountStatus, final ACRType acrType, final String defrdRTPExpDateTime,
            final CurrencyAmountStatus defrdRTPAgrmtAmount, final CurrencyAmountStatus defrdRTPMaxAgrdAmount, final ParameterStatus defrdAmountDetailsStatus) {

        final ScenarioType scenarioType = getScenarioType(rtpType, currencyAmountStatus, acrType, defrdRTPExpDateTime, defrdRTPAgrmtAmount, defrdRTPMaxAgrdAmount,
                defrdAmountDetailsStatus);

        final StringBuilder stringBuilder = new StringBuilder();
        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("PaymentRequest.P%d", getNextPositiveScenarioId());
                expectedResultString = "Payment Request is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("PaymentRequest.N%d", getNextNegativeScenarioId());
                expectedResultString = "Payment Request is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append(": ").append('\n');

        if (rtpType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the RTP Type is \"%s\"\n", rtpType.name()));
        }
        if (acrType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Authentication check required type is \"%s\"\n", acrType.name()));
        }
        if (defrdRTPExpDateTime != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Deferred request to pay expiry datetime is \"%s\"\n", defrdRTPExpDateTime));
        }
        if (defrdRTPAgrmtAmount != CurrencyAmountStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Deferred request to pay agreement amount is \"%s\"\n", defrdRTPAgrmtAmount));
        }
        if (defrdRTPMaxAgrdAmount != CurrencyAmountStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag())
                    .append(String.format("the Deferred request to pay maximum agreed amount is \"%s\"\n", defrdRTPMaxAgrdAmount));
        }
        if (defrdAmountDetailsStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Deferred amount details is \"%s\"\n", defrdAmountDetailsStatus.name()));
        }

        stringBuilder.append("    When I try to create the Payment Request\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param rtpType           The payment request type.
     * @param paymentType       The payment type.
     * @param checkoutType      The checkout type.
     * @param billDetailsStatus The bill details object status.
     * @return The text of the test scenario.
     */
    private String generateScenario(final RTPType rtpType, final PaymentType paymentType, final CheckoutType checkoutType, final ParameterStatus billDetailsStatus) {

        final ScenarioType scenarioType = getScenarioType(rtpType, paymentType, checkoutType, billDetailsStatus);
        final StringBuilder stringBuilder = new StringBuilder();
        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("PaymentRequest.P%d", getNextPositiveScenarioId());
                expectedResultString = "Payment Request is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("PaymentRequest.N%d", getNextNegativeScenarioId());
                expectedResultString = "Payment Request is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append(": ").append('\n');

        if (rtpType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the RTP Type is \"%s\"\n", rtpType.name()));
        }
        if (paymentType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Payment Type is \"%s\"\n", paymentType.name()));
        }
        if (checkoutType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Checkout Type is \"%s\"\n", checkoutType.name()));
        }
        if (billDetailsStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Bill Details is \"%s\"\n", billDetailsStatus.name()));
        }

        stringBuilder.append("    When I try to create the Payment Request\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    /**
     * Generate BDD test scenario based on given parameters.
     *
     * @param checkoutType  The checkout type.
     * @param deliveryType  The delivery type.
     * @param addressStatus The address status.
     * @return The text of the test scenario.
     */
    private String generateScenario(final CheckoutType checkoutType, final ParameterStatus addressStatus, final DeliveryType deliveryType) {

        final ScenarioType scenarioType = getScenarioType(checkoutType, addressStatus, deliveryType);
        final StringBuilder stringBuilder = new StringBuilder();
        final String scenarioId;
        final String expectedResultString;
        switch (scenarioType) {
            case POSITIVE:
                scenarioId = String.format("PaymentRequest.P%d", getNextPositiveScenarioId());
                expectedResultString = "Payment Request is created successfully\n";
                break;
            case NEGATIVE:
                scenarioId = String.format("PaymentRequest.N%d", getNextNegativeScenarioId());
                expectedResultString = "Payment Request is not created\n";
                break;
            case DUPLICATE_NEGATIVE:
                //skip test scenario because it is duplicate
                return "";
            default:
                throw new IllegalArgumentException("Unknown scenario type: " + scenarioType);
        }
        stringBuilder.append("  Scenario: ").append(scenarioId).append(": ").append('\n');

        if (checkoutType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Checkout Type is \"%s\"\n", checkoutType.name()));
        }
        if (addressStatus != ParameterStatus.is_null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Address is \"%s\"\n", addressStatus.name()));
        }
        if (deliveryType != null) {
            stringBuilder.append(getNextPreconditionTag()).append(String.format("the Delivery Type is \"%s\"\n", deliveryType.name()));
        }

        stringBuilder.append("    When I try to create the Payment Request\n");

        stringBuilder.append("    Then ").append(expectedResultString).append('\n');

        return stringBuilder.toString();
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param rtpType        The payment request type.
     * @param paymentType    The payment type.
     * @param checkoutType   The checkout type.
     * @param deliveryType   The delivery type.
     * @param merchantStatus The merchant status.
     * @return The text of the test scenario.
     */
    private ScenarioType getScenarioType(final RTPType rtpType, final DeliveryType deliveryType, final ParameterStatus merchantStatus,
            final PaymentType paymentType, final CheckoutType checkoutType) {
        //Requires rtp type
        if (isNull(rtpType)) {
            return getNegativeScenarioType(getFingerprint(rtpType));
        }
        //Requires delivery type
        if (isNull(deliveryType)) {
            return getNegativeScenarioType(getFingerprint(rtpType, deliveryType));
        }
        //Requires merchant
        if (isNull(merchantStatus)) {
            return getNegativeScenarioType(getFingerprint(rtpType, deliveryType, merchantStatus));
        }
        //Requires payment type
        if (isNull(paymentType)) {
            return getNegativeScenarioType(getFingerprint(rtpType, deliveryType, merchantStatus, paymentType));
        }
        //Requires payment type
        if (isNull(checkoutType)) {
            return getNegativeScenarioType(getFingerprint(rtpType, deliveryType, merchantStatus, paymentType, checkoutType));
        }

        return ScenarioType.DUPLICATE_NEGATIVE;
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param rtpType                  The payment request type.
     * @param acrType                  The authentication check required type.
     * @param defrdRTPExpDateTime      The deferred Request To Pay Expiry Datetime.
     * @param defrdRTPAgrmtAmount      The deferred Request To Pay Agreement Amount.
     * @param defrdRTPMaxAgrdAmount    The deferred Request To Pay Maximum Agreed Amount.
     * @param defrdAmountDetailsStatus The deferred amount details.
     * @return The text of the test scenario.
     */
    private ScenarioType getScenarioType(final RTPType rtpType, final CurrencyAmountStatus currencyAmountStatus, final ACRType acrType, final String defrdRTPExpDateTime,
            final CurrencyAmountStatus defrdRTPAgrmtAmount, final CurrencyAmountStatus defrdRTPMaxAgrdAmount, final ParameterStatus defrdAmountDetailsStatus) {

        if (isNull(rtpType)) {
            return ScenarioType.DUPLICATE_NEGATIVE;
        } else {
            switch (rtpType) {
                case IMMEDIATE:
                    if (currencyAmountStatus != CurrencyAmountStatus.positive) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus));
                    }
                    if (!isNull(acrType)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType));
                    }
                    if (!isNull(defrdRTPExpDateTime)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType, defrdRTPExpDateTime));
                    }
                    if (!isNull(defrdRTPAgrmtAmount)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType, defrdRTPExpDateTime, defrdRTPAgrmtAmount));
                    }
                    if (!isNull(defrdRTPMaxAgrdAmount)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType, defrdRTPExpDateTime, defrdRTPAgrmtAmount,
                                defrdRTPMaxAgrdAmount));
                    }
                    if (!isNull(defrdAmountDetailsStatus)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType, defrdRTPExpDateTime, defrdRTPAgrmtAmount,
                                defrdRTPMaxAgrdAmount, defrdAmountDetailsStatus));
                    }
                    break;
                case DEFERRED:
                    if (!isNull(currencyAmountStatus)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus));
                    }
                    if (isNull(acrType)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType));
                    }
                    if (isNull(defrdRTPAgrmtAmount)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType, defrdRTPAgrmtAmount));
                    }
                    if (isNull(defrdRTPMaxAgrdAmount)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, currencyAmountStatus, acrType, defrdRTPAgrmtAmount, defrdRTPMaxAgrdAmount));
                    }
                    break;
                default:
                    break;
            }
        }
        return ScenarioType.DUPLICATE_NEGATIVE;
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param rtpType           The payment request type.
     * @param paymentType       The payment type.
     * @param checkoutType      The checkout type.
     * @param billDetailsStatus The bill details object status.
     * @return The text of the test scenario.
     */
    private ScenarioType getScenarioType(final RTPType rtpType, final PaymentType paymentType, final CheckoutType checkoutType, final ParameterStatus billDetailsStatus) {
        if (isNull(rtpType) || isNull(paymentType) || isNull(checkoutType)) {
            return ScenarioType.DUPLICATE_NEGATIVE;
        } else {
            //Requires payment type
            switch (paymentType) {
                case BILL_PAY:
                    if (rtpType != RTPType.IMMEDIATE) {
                        return getNegativeScenarioType(getFingerprint(rtpType, paymentType));
                    }
                    if (checkoutType != CheckoutType.NORMAL) {
                        return getNegativeScenarioType(getFingerprint(rtpType, paymentType, checkoutType));
                    }
                    //Requires bill details
                    if (!isRequired(billDetailsStatus)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, paymentType, checkoutType, billDetailsStatus));
                    }
                    break;
                case SMB:
                    if (rtpType != RTPType.IMMEDIATE) {
                        return getNegativeScenarioType(getFingerprint(rtpType, paymentType));
                    }
                    if (checkoutType != CheckoutType.NORMAL) {
                        return getNegativeScenarioType(getFingerprint(rtpType, paymentType, checkoutType));
                    }
                    if (!isNull(billDetailsStatus)) {
                        return getNegativeScenarioType(getFingerprint(rtpType, paymentType, checkoutType, billDetailsStatus));
                    }
                    break;
                default:
                    if (!isNull(billDetailsStatus)) {
                        return getNegativeScenarioType(getFingerprint(paymentType, billDetailsStatus));
                    }
                    break;
            }
        }
        return ScenarioType.DUPLICATE_NEGATIVE;
    }

    /**
     * Generate the expected result of the test scenario based on given parameters.
     *
     * @param checkoutType  The checkout type.
     * @param deliveryType  The delivery type.
     * @param addressStatus The address status.
     * @return The text of the test scenario.
     */
    private ScenarioType getScenarioType(final CheckoutType checkoutType, final ParameterStatus addressStatus, final DeliveryType deliveryType) {
        if (isNull(deliveryType) || isNull(checkoutType)) {
            return ScenarioType.DUPLICATE_NEGATIVE;
        } else {
            switch (checkoutType) {
                case QUICK:
                    //Requires address
                    if (!isNull(addressStatus)) {
                        return getNegativeScenarioType(getFingerprint(checkoutType, addressStatus));
                    }
                    //Requires delivery type
                    if (!isDeliveryTypeIn(deliveryType, DeliveryType.ADDRESS, DeliveryType.DIGITAL)) {
                        return getNegativeScenarioType(getFingerprint(checkoutType, addressStatus, deliveryType));
                    }
                    break;
                default:
                    break;
            }
        }
        return ScenarioType.DUPLICATE_NEGATIVE;
    }

    @Override
    public void dumpScenarioFingerprintsHeader() {
        System.out.println("rtpType | deliveryType | merchantStatus | paymentType | checkoutType|  currencyAmountStatus | acrType | defrdRTPExpDateTime | "
                + "defrdRTPAgrmtAmount | defrdRTPMaxAgrdAmount | defrdAmountDetailsStatus | addressStatus | billDetailsStatus");
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

        fileWriter.println("Feature: Payment Request\n");

        final List<RTPType> rtpTypeList = new LinkedList<RTPType>(Arrays.asList(RTPType.values()));
        rtpTypeList.add(0, null);

        final List<PaymentType> paymentTypeList = new LinkedList<PaymentType>(Arrays.asList(PaymentType.values()));
        paymentTypeList.add(0, null);

        final List<ParameterStatus> billDetailsStatusList = new LinkedList<ParameterStatus>(Arrays.asList(ParameterStatus.values()));

        final List<CheckoutType> checkoutTypeList = new LinkedList<CheckoutType>(Arrays.asList(CheckoutType.values()));
        checkoutTypeList.add(0, null);

        final List<ParameterStatus> addressStatusList = new LinkedList<ParameterStatus>(Arrays.asList(ParameterStatus.values()));

        final List<DeliveryType> deliveryTypeList = new LinkedList<DeliveryType>(Arrays.asList(DeliveryType.values()));
        deliveryTypeList.add(0, null);

        final List<CurrencyAmountStatus> currencyAmountStatusList = new LinkedList<CurrencyAmountStatus>(Arrays.asList(CurrencyAmountStatus.values()));

        final List<ParameterStatus> merchantStatusList = new LinkedList<ParameterStatus>(Arrays.asList(ParameterStatus.values()));

        final List<ACRType> acrTypesList = new LinkedList<ACRType>(Arrays.asList(ACRType.values()));
        acrTypesList.add(0, null);

        final String[] defrdRTPExpDateTimeList = {null, "2015-01-01"};

        final List<CurrencyAmountStatus> defrdRTPAgrmtAmountList = new LinkedList<CurrencyAmountStatus>(Arrays.asList(CurrencyAmountStatus.values()));

        final List<CurrencyAmountStatus> defrdRTPMaxAgrdAmountList = new LinkedList<CurrencyAmountStatus>(Arrays.asList(CurrencyAmountStatus.values()));

        final ParameterStatus[] amountDetailsList = {ParameterStatus.is_null, ParameterStatus.valid};

        resetPreconditionTagging();

        for (final RTPType rtpType : rtpTypeList) {
            for (final DeliveryType deliveryType : deliveryTypeList) {
                for (final ParameterStatus merchantStatus : merchantStatusList) {
                    for (final PaymentType paymentType : paymentTypeList) {
                        for (final CheckoutType checkoutType : checkoutTypeList) {
                            final String scenario = generateScenario(rtpType, deliveryType, merchantStatus, paymentType, checkoutType);
                            fileWriter.print(scenario);
                        }
                    }
                }
            }
        }

        for (final RTPType rtpType : rtpTypeList) {
            for (final CurrencyAmountStatus currencyAmountStatus : currencyAmountStatusList) {
                for (final ACRType acrType : acrTypesList) {
                    for (final String defrdRTPExpDateTime : defrdRTPExpDateTimeList) {
                        for (final CurrencyAmountStatus defrdRTPAgrmtAmount : defrdRTPAgrmtAmountList) {
                            for (final CurrencyAmountStatus defrdRTPMaxAgrdAmount : defrdRTPMaxAgrdAmountList) {
                                for (final ParameterStatus defrdAmountDetailsStatus : amountDetailsList) {
                                    final String scenario = generateScenario(rtpType, currencyAmountStatus, acrType, defrdRTPExpDateTime, defrdRTPAgrmtAmount,
                                            defrdRTPMaxAgrdAmount, defrdAmountDetailsStatus);
                                    fileWriter.print(scenario);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (final RTPType rtpType : rtpTypeList) {
            for (final PaymentType paymentType : paymentTypeList) {
                for (final CheckoutType checkoutType : checkoutTypeList) {
                    for (final ParameterStatus billDetailsStatus : billDetailsStatusList) {
                        final String scenario = generateScenario(rtpType, paymentType, checkoutType, billDetailsStatus);
                        fileWriter.print(scenario);
                    }
                }
            }
        }

        for (final CheckoutType checkoutType : checkoutTypeList) {
            for (final ParameterStatus addressStatus : addressStatusList) {
                for (final DeliveryType deliveryType : deliveryTypeList) {
                    final String scenario = generateScenario(checkoutType, addressStatus, deliveryType);
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
            final PaymentRequestFeatureFileGenerator generator = new PaymentRequestFeatureFileGenerator();
            if (args.length == 0) {
                generator.autoGenerateFeatureFile();
            } else if (args.length == 1) {
                generator.generate(args[0]);
            } else {
                System.err.println("Usage: java " + PaymentRequestFeatureFileGenerator.class.getSimpleName() + " <BDD test output file name>");
                System.exit(-1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

