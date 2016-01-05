package com.zapp.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test helper methods like setters/getters/toString/equals/hashCode ...
 */
@SuppressWarnings("HardcodedFileSeparator")
public class TestHelpers {

    /**
     * Bill details date formatter.
     */
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private List<IValidatable> models;

    public TestHelpers() {
        models = new ArrayList<IValidatable>();
    }

    @Given("^\"(.*?)\" implements \"(.*?)\" interface$")
    public void implements_interface(String className, String interfaceName) throws ClassNotFoundException {
        final Class clazz = Class.forName(className);
        final boolean implementsInterface = implementsInterface(clazz, interfaceName);
        assertTrue(implementsInterface);
    }

    /**
     * Checks if a class represented by the clazz parameter implements the interface with the given name.
     *
     * @param clazz         The class representation of the object which should implement the interface.
     * @param interfaceName The name of th interface which should be implemented
     * @return true if the class or its superclasses implements such interface.
     */
    private boolean implementsInterface(final Class clazz, final String interfaceName) {
        final Class interfaces[] = clazz.getInterfaces();
        boolean implementsInterface = false;
        for (final Class i : interfaces) {
            implementsInterface = interfaceName.equals(i.getName());
            if (implementsInterface) {
                break;
            }
        }

        final Class superclass = clazz.getSuperclass();
        if (!implementsInterface && !"java.lang.Object".equals(superclass)) {
            implementsInterface = implementsInterface(superclass, interfaceName);
        }
        return implementsInterface;
    }

    @Given("^the \"(.*?)\" interface should be implemented by the following models:$")
    public void the_interface_should_be_implemented_by_the_following_models(String interfaceName, List<String> models) throws Throwable {
        for (String className : models) {
            implements_interface(className, interfaceName);
        }
    }

    @Given("^I have the following bank accounts:$")
    public void i_have_the_following_bank_accounts(List<Map<String, String>> values) throws ZappModelValidationException {
        for (Map<String, String> map : values) {
            final BankAccount account = Defaults.getDefaultBankAccount();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "name":
                        account.setName(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, account.getName());
                        break;
                    case "number":
                        account.setNumber(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, account.getNumber());
                        break;
                    case "sortCode":
                        account.setSortCode(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, account.getSortCode());
                        break;
                    case "balance":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final CurrencyAmount balance = Defaults.defaultValue(CurrencyAmount.class);
                            account.setBalance(balance);
                            assertEquals("Incorrect setter implementation for: " + key, balance, account.getBalance());
                        }
                        break;
                    case "availableFunds":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final CurrencyAmount availableFunds = Defaults.defaultValue(CurrencyAmount.class);
                            account.setAvailableFunds(availableFunds);
                            assertEquals("Incorrect setter implementation for: " + key, availableFunds, account.getAvailableFunds());
                        }
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for bank account");
                }
            }
            models.add(account);
        }
    }

    @Then("^they are equal when I compare them$")
    public void they_are_equal_when_I_compare_them() {
        assertEquals("Expected two models in the list", 2, models.size());
        assertEquals("Models are expected to be equal", models.get(0), models.get(1));
    }

    @Then("^the string representation should be \"(.*?)\"$")
    public void the_string_representation_should_be(String arg1) {
        for (IValidatable model : models) {
            assertEquals("String representations don't match", arg1, model.toString());
        }
    }

    @Then("^the hash codes should be equal$")
    public void the_hash_code_should_be() throws Throwable {
        assertEquals("Hash codes don't match", models.get(0).hashCode(), models.get(1).hashCode());
    }

    @When("^I remove one of the required fields from the \"(.*?)\"$")
    public void i_remove_one_of_the_required_fields_from_the(String arg1) {
        switch (arg1) {
            case "BankAccount":
                ((BankAccount) models.get(0)).setName("");
                break;
            default:
                throw new RuntimeException("Invalid field name");
        }
    }

    @Then("^they are not equal when I compare them$")
    public void they_are_not_equal_when_I_compare_them() {
        assertNotEquals("Objects should be different", models.get(0), models.get(1));
    }

    @Given("^I have the following bill details:$")
    public void i_have_the_following_bill_details(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final BillDetails billDetails = Defaults.defaultValue(BillDetails.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "accountId":
                        billDetails.setAccountId(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, billDetails.getAccountId());
                        break;
                    case "reference":
                        billDetails.setReference(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, billDetails.getReference());
                        break;
                    case "periodFrom":
                        final Date periodFrom = DATE_FORMAT.parse(value);
                        billDetails.setPeriodFrom(periodFrom);
                        assertEquals("Incorrect setter implementation for: " + key, periodFrom, billDetails.getPeriodFrom());
                        break;
                    case "periodTo":
                        final Date periodTo = DATE_FORMAT.parse(value);
                        billDetails.setPeriodTo(periodTo);
                        assertEquals("Incorrect setter implementation for: " + key, periodTo, billDetails.getPeriodTo());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for bill details");
                }
            }
            models.add(billDetails);
        }
    }

    @Then("^\"(.*?)\" is created successfully$")
    public void is_created_successfully(String arg1) throws Throwable {
        assertTrue("No model has been created", !models.isEmpty());
    }

    @Given("^I have two identical instances of \"(.*?)\"$")
    public void i_have_two_identical_instances_of(String arg1) throws Throwable {
        switch (arg1) {
            case "DeliveryAddress":
                models.add(Defaults.getDefaultDeliveryAddress());
                models.add(Defaults.getDefaultDeliveryAddress());
                break;
            default:
                throw new RuntimeException("Invalid model name");
        }
    }

    @Given("^I have the following currency amount:$")
    public void i_have_the_following_currency_amount(List<Map<String, String>> values) {
        for (Map<String, String> map : values) {
            final CurrencyAmount currencyAmount = Defaults.defaultValue(CurrencyAmount.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "value":
                        currencyAmount.setValue(Long.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, value, currencyAmount.getValue().toString());
                        break;
                    case "currencyCode":
                        currencyAmount.setCurrencyCode(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, currencyAmount.getCurrencyCode());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for currency amount");
                }
            }
            models.add(currencyAmount);
        }
    }

    @When("^I change identifier of one of the addresses$")
    public void i_change_identifier_of_one_of_the_addresses() throws Throwable {
        final DeliveryAddress address1 = (DeliveryAddress) models.get(0);
        address1.setIdentifier("differentId");
    }

    @Given("^I have the following merchants:$")
    public void i_have_the_following_merchants(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final Merchant merchant = Defaults.getDefaultMerchant();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "identifier":
                        merchant.setIdentifier(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, merchant.getIdentifier());
                        break;
                    case "name":
                        merchant.setName(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, merchant.getName());
                        break;
                    case "email":
                        merchant.setEmail(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, merchant.getEmail());
                        break;
                    case "phone":
                        merchant.setPhone(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, merchant.getPhone());
                        break;
                    case "address":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final Address address = Defaults.defaultValue(Address.class);
                            merchant.setAddress(address);
                            assertEquals("Incorrect setter implementation for: " + key, address, merchant.getAddress());
                        }
                        break;
                    case "website":
                        merchant.setWebsite(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, merchant.getWebsite());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for merchant");
                }
            }
            models.add(merchant);
        }
    }

    @Given("^I have the following PaymentAuth's:$")
    public void i_have_the_following_PaymentAuth_s(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final PaymentAuth paymentAuth = Defaults.getDefaultPaymentAuth();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "checkoutType":
                        paymentAuth.setCheckoutType(CheckoutType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, CheckoutType.valueOf(value), paymentAuth.getCheckoutType());
                        break;
                    case "deliveryType":
                        paymentAuth.setDeliveryType(DeliveryType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, DeliveryType.valueOf(value), paymentAuth.getDeliveryType());
                        break;
                    case "bankAccount":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final BankAccount bankAccount = Defaults.defaultValue(BankAccount.class);
                            paymentAuth.setBankAccount(bankAccount);
                            assertEquals("Incorrect setter implementation for: " + key, bankAccount, paymentAuth.getBankAccount());
                        }
                        break;
                    case "user":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final User user = Defaults.defaultValue(User.class);
                            paymentAuth.setUser(user);
                            assertEquals("Incorrect setter implementation for: " + key, user, paymentAuth.getUser());
                        }
                        break;
                    case "finalAmount":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final CurrencyAmount currencyAmount = Defaults.defaultValue(CurrencyAmount.class);
                            paymentAuth.setFinalAmount(currencyAmount);
                            assertEquals("Incorrect setter implementation for: " + key, currencyAmount, paymentAuth.getFinalAmount());
                        }
                        break;
                    case "deliveryAddress":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final DeliveryAddress deliveryAddress = Defaults.defaultValue(DeliveryAddress.class);
                            paymentAuth.setDeliveryAddress(deliveryAddress);
                            assertEquals("Incorrect setter implementation for: " + key, deliveryAddress, paymentAuth.getDeliveryAddress());
                        }
                        break;
                    case "transactionStatus":
                        paymentAuth.setTransactionStatus(TransactionStatus.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, TransactionStatus.valueOf(value), paymentAuth.getTransactionStatus());
                        break;
                    case "payconnectEnabled":
                        final Boolean payconnectEnabled = Boolean.valueOf(value);
                        paymentAuth.setPayconnectEnabled(payconnectEnabled);
                        assertEquals("Incorrect setter implementation for: " + key, payconnectEnabled, paymentAuth.isPayconnectEnabled());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for PaymentAuth");
                }
            }
            models.add(paymentAuth);
        }
    }

    @Given("^I have the following PaymentRequest's:$")
    public void i_have_the_following_PaymentRequest_s(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final PaymentRequest paymentRequest = Defaults.getDefaultPaymentRequest();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                final CurrencyAmount amount = Defaults.defaultValue(CurrencyAmount.class);
                switch (key) {
                    case "rtpType":
                        paymentRequest.setRtpType(RTPType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, RTPType.valueOf(value), paymentRequest.getRtpType());
                        break;
                    case "paymentType":
                        paymentRequest.setPaymentType(PaymentType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, PaymentType.valueOf(value), paymentRequest.getPaymentType());
                        break;
                    case "checkoutType":
                        paymentRequest.setCheckoutType(CheckoutType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, CheckoutType.valueOf(value), paymentRequest.getCheckoutType());
                        break;
                    case "deliveryType":
                        paymentRequest.setDeliveryType(DeliveryType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, DeliveryType.valueOf(value), paymentRequest.getDeliveryType());
                        break;
                    case "messageType":
                        paymentRequest.setMessageType(MessageType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, value, paymentRequest.getMessageType().toString());
                        break;
                    case "user":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final User user = Defaults.defaultValue(User.class);
                            paymentRequest.setUser(user);
                            assertEquals("Incorrect setter implementation for: " + key, user, paymentRequest.getUser());
                        }
                        break;
                    case "billDetails":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final BillDetails billDetails = Defaults.defaultValue(BillDetails.class);
                            paymentRequest.setBillDetails(billDetails);
                            assertEquals("Incorrect setter implementation for: " + key, billDetails, paymentRequest.getBillDetails());
                        }
                        break;
                    case "amount":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            paymentRequest.setAmount(amount);
                            assertEquals("Incorrect setter implementation for: " + key, amount, paymentRequest.getAmount());
                        }
                        break;
                    case "merchant":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final Merchant merchant = Defaults.defaultValue(Merchant.class);
                            paymentRequest.setMerchant(merchant);
                            assertEquals("Incorrect setter implementation for: " + key, merchant, paymentRequest.getMerchant());
                        }
                        break;
                    case "address":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final Address address = Defaults.defaultValue(Address.class);
                            paymentRequest.setAddress(address);
                            assertEquals("Incorrect setter implementation for: " + key, address, paymentRequest.getAddress());
                        }
                        break;
                    case "merchantCallbackUrl":
                        paymentRequest.setMerchantCallbackUrl(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, paymentRequest.getMerchantCallbackUrl());
                        break;
                    case "acrType":
                        paymentRequest.setAcrType(ACRType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, ACRType.valueOf(value), paymentRequest.getAcrType());
                        break;
                    case "defrdRTPExpDateTime":
                        paymentRequest.setDefrdRTPExpDateTime(DATE_FORMAT.parse(value));
                        assertEquals("Incorrect setter implementation for: " + key, DATE_FORMAT.parse(value), paymentRequest.getDefrdRTPExpDateTime());
                        break;
                    case "defrdRTPAgrmtAmount":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            paymentRequest.setDefrdRTPAgrmtAmount(amount);
                            assertEquals("Incorrect setter implementation for: " + key, amount, paymentRequest.getDefrdRTPAgrmtAmount());
                        }
                        break;
                    case "defrdRTPMaxAgrdAmount":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            paymentRequest.setDefrdRTPMaxAgrdAmount(amount);
                            assertEquals("Incorrect setter implementation for: " + key, amount, paymentRequest.getDefrdRTPMaxAgrdAmount());
                        }
                        break;
                    case "defrdAmountDetails":
                        final AmountDetail[] amountDetails = Defaults.defaultValue(AmountDetail[].class);
                        paymentRequest.setDefrdAmountDetails(amountDetails);
                        assertArrayEquals("Incorrect setter implementation for: " + key, amountDetails, paymentRequest.getDefrdAmountDetails());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for PaymentRequest");
                }
            }
            models.add(paymentRequest);
        }
    }

    @Given("^I have the following transactions:$")
    public void i_have_the_following_transactions(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final Transaction transaction = Defaults.getDefaultTransaction();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "brn":
                        transaction.setBrn(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, transaction.getBrn());
                        break;
                    case "transactionId":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final TransactionId transactionId = Defaults.defaultValue(TransactionId.class);
                            transaction.setTransactionId(transactionId);
                            assertEquals("Incorrect setter implementation for: " + key, transactionId, transaction.getTransactionId());
                        }
                        break;
                    case "paymentRequest":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final PaymentRequest paymentRequest = Defaults.defaultValue(PaymentRequest.class);
                            transaction.setPaymentRequest(paymentRequest);
                            assertEquals("Incorrect setter implementation for: " + key, paymentRequest, transaction.getPaymentRequest());
                        }
                        break;
                    case "paymentAuth":
                        if (ParameterStatus.valid.toString().equals(value)) {
                            final PaymentAuth paymentAuth = Defaults.defaultValue(PaymentAuth.class);
                            transaction.setPaymentAuth(paymentAuth);
                            assertEquals("Incorrect setter implementation for: " + key, paymentAuth, transaction.getPaymentAuth());
                        }
                        break;
                    case "confirmationExpiryInterval":
                        transaction.setConfirmationExpiryInterval(Integer.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, value, transaction.getConfirmationExpiryInterval().toString());
                        break;
                    case "retrievalExpiryInterval":
                        transaction.setRetrievalExpiryInterval(Integer.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, value, transaction.getRetrievalExpiryInterval().toString());
                        break;
                    case "retrievalMethod":
                        transaction.setRetrievalMethod(RetrievalMethod.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, value, transaction.getRetrievalMethod().toString());
                        break;
                    case "merchantCallbackUrl":
                        transaction.setMerchantCallbackUrl(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, transaction.getMerchantCallbackUrl());
                        break;
                    case "askForPayconnect":
                        final Boolean askForPayconnect = Boolean.valueOf(value);
                        transaction.setAskForPayconnect(askForPayconnect);
                        assertEquals("Incorrect setter implementation for: " + key, askForPayconnect, transaction.isAskForPayconnect());
                        break;
                    case "initiatorIsAuthorizer":
                        final Boolean initiatorIsAuthorizer = Boolean.valueOf(value);
                        transaction.setInitiatorIsAuthorizer(initiatorIsAuthorizer);
                        assertEquals("Incorrect setter implementation for: " + key, initiatorIsAuthorizer, transaction.getInitiatorIsAuthorizer());
                        break;
                    case "settlementRetrievalId":
                        transaction.setSettlementRetrievalId(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, transaction.getSettlementRetrievalId());
                        break;
                    case "notificationSent":
                        final Boolean notificationSent = Boolean.valueOf(value);
                        transaction.setNotificationSent(notificationSent);
                        assertEquals("Incorrect setter implementation for: " + key, notificationSent, transaction.isNotificationSent());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for Transaction");
                }
            }
            models.add(transaction);
        }
    }

    @When("^I remove the transactionId field$")
    public void i_remove_the_transactionId_field() throws Throwable {
        ((Transaction) models.get(0)).setTransactionId(null);
        ((Transaction) models.get(1)).setTransactionId(null);
    }

    @When("^I set the brn code to \"(.*?)\"$")
    public void i_set_the_brn_code_to(String arg1) throws Throwable {
        ((Transaction) models.get(0)).setBrn(arg1);
    }

    @Then("^transaction status value returns \"(.*?)\"$")
    public void transaction_status_value_returns(String arg1) throws Throwable {
        assertEquals(TransactionStatus.valueOf(arg1), ((Transaction) models.get(0)).getTransactionStatus());
    }

    @When("^I set the transaction status for PaymentAuth to be \"(.*?)\"$")
    public void i_set_the_transaction_status_for_PaymentAuth_to_be(String arg1) throws Throwable {
        ((Transaction) models.get(0)).setPaymentAuth(new PaymentAuth(null, null, null, null, null, null, TransactionStatus.valueOf(arg1), false));
    }

    @Given("^I have the following transaction ids:$")
    public void i_have_the_following_transaction_ids(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final TransactionId transactionId = Defaults.getDefaultTransactionId();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "aptId":
                        transactionId.setAptId(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, transactionId.getAptId());
                        break;
                    case "aptrId":
                        transactionId.setAptrId(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, transactionId.getAptrId());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for TransactionId");
                }
            }
            models.add(transactionId);
        }
    }

    @Given("^I have the following users:$")
    public void i_have_the_following_users(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final User user = Defaults.getDefaultUser();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "firstName":
                        user.setFirstName(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, user.getFirstName());
                        break;
                    case "lastName":
                        user.setLastName(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, user.getLastName());
                        break;
                    case "middleName":
                        user.setMiddleName(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, user.getMiddleName());
                        break;
                    case "title":
                        user.setTitle(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, user.getTitle());
                        break;
                    case "email":
                        user.setEmail(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, user.getEmail());
                        break;
                    case "phone":
                        user.setPhone(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, user.getPhone());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for User");
                }
            }
            models.add(user);
        }
    }

    @Given("^I have the following amount detail:$")
    public void i_have_the_following_amount_detail(List<Map<String, String>> values) throws Throwable {
        for (Map<String, String> map : values) {
            final AmountDetail amountDetail = Defaults.getDefaultAmountDetails();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String value = entry.getValue();
                final String key = entry.getKey();
                switch (key) {
                    case "type":
                        amountDetail.setType(AmountType.valueOf(value));
                        assertEquals("Incorrect setter implementation for: " + key, AmountType.valueOf(value), amountDetail.getType());
                        break;
                    case "description":
                        amountDetail.setDescription(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, amountDetail.getDescription());
                        break;
                    case "price":
                        amountDetail.setPrice(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, amountDetail.getPrice());
                        break;
                    case "rate":
                        amountDetail.setRate(value);
                        assertEquals("Incorrect setter implementation for: " + key, value, amountDetail.getRate());
                        break;
                    default:
                        throw new RuntimeException("Invalid field name for currency amount");
                }
            }
            models.add(amountDetail);
        }
    }
}
