package com.zapp.core;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides default values for all Zapp core and Java types.
 */
public final class Defaults {

    private Defaults() {
    }

    /**
     * A static map with default values for all types.
     */
    private static final Map<Class<?>, Object> DEFAULTS;

    /**
     * A static map with default invalid values for Zapp Core types.
     */
    private static final Map<Class<?>, Object> DEFAULT_INVALIDS;

    static {
        final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
        try {
            put(map, boolean.class, false);
            put(map, char.class, '\0');
            put(map, byte.class, (byte) 0);
            put(map, short.class, (short) 0);
            put(map, int.class, 0);
            put(map, long.class, 0L);
            put(map, float.class, 0f);
            put(map, double.class, 0d);
            put(map, String.class, "");
            put(map, CurrencyAmount.class, getDefaultCurrencyAmount());
            put(map, Address.class, getDefaultAddress());
            put(map, BankAccount.class, getDefaultBankAccount());
            put(map, DeliveryAddress.class, getDefaultDeliveryAddress());
            put(map, User.class, getDefaultUser());
            put(map, BillDetails.class, getDefaultBillDetails());
            put(map, Merchant.class, getDefaultMerchant());
            put(map, PaymentAuth.class, getDefaultPaymentAuth());
            put(map, PaymentRequest.class, getDefaultPaymentRequest());
            put(map, TransactionId.class, getDefaultTransactionId());
            put(map, Transaction.class, getDefaultTransaction());
            put(map, AmountDetail.class, getDefaultAmountDetails());
        } catch (ZappModelValidationException zmve) {
            zmve.printStackTrace();
            System.exit(-1);
        }
        DEFAULTS = Collections.unmodifiableMap(map);

        final Map<Class<?>, Object> map2 = new HashMap<Class<?>, Object>();
        try {
            put(map2, CurrencyAmount.class, getDefaultInvalidCurrencyAmount());
            put(map2, Address.class, getDefaultInvalidAddress());
            put(map2, BankAccount.class, getDefaultInvalidBankAccount());
            put(map2, DeliveryAddress.class, getDefaultInvalidDeliveryAddress());
            put(map2, User.class, getDefaultInvalidUser());
            put(map2, BillDetails.class, getDefaultInvalidBillDetails());
            put(map2, Merchant.class, getDefaultInvalidMerchant());
            put(map2, PaymentAuth.class, getDefaultInvalidPaymentAuth());
            put(map2, PaymentRequest.class, getDefaultInvalidPaymentRequest());
            put(map2, TransactionId.class, getDefaultInvalidTransactionId());
        } catch (ZappModelValidationException zmve) {
            zmve.printStackTrace();
            System.exit(-2);
        }
        DEFAULT_INVALIDS = Collections.unmodifiableMap(map2);
    }

    /**
     * @return Default {@link Transaction}.
     */
    public static Transaction getDefaultTransaction() throws ZappModelValidationException {
        return new Transaction("AAAAAA", getDefaultTransactionId(), null, null, 300, 400, RetrievalMethod.INVOCATION, "callBackUrl", false, false, "dsfgds", false);
    }

    /**
     * @return Default {@link TransactionId}.
     */
    public static TransactionId getDefaultTransactionId() throws ZappModelValidationException {
        return new TransactionId("aptId", "aptrId");
    }

    /**
     * @return Default invalid {@link TransactionId}.
     */
    private static TransactionId getDefaultInvalidTransactionId() throws ZappModelValidationException {
        final TransactionId transactionId = new TransactionId("aptId", "aptrId");
        transactionId.setAptId(null);
        return transactionId;
    }

    /**
     * @return Default {@link PaymentRequest}.
     */
    public static PaymentRequest getDefaultPaymentRequest() throws ZappModelValidationException {
        return new PaymentRequest(RTPType.IMMEDIATE, PaymentType.INSTANT_PAYMENT, CheckoutType.NORMAL, DeliveryType.ADDRESS, getDefaultUser(),
                null, getDefaultCurrencyAmount(), getDefaultMerchant(), getDefaultAddress(), "merchantCallBackUrl", null, null, null, null, null);
    }

    /**
     * @return Default invalid {@link PaymentRequest}.
     */
    private static PaymentRequest getDefaultInvalidPaymentRequest() throws ZappModelValidationException {
        final PaymentRequest paymentRequest = new PaymentRequest(RTPType.IMMEDIATE, PaymentType.INSTANT_PAYMENT, CheckoutType.NORMAL, DeliveryType.ADDRESS,
                getDefaultUser(), null, getDefaultCurrencyAmount(), getDefaultMerchant(), getDefaultAddress(), "merchantCallBackUrl", null, null, null, null, null);
        paymentRequest.setCheckoutType(null);
        return paymentRequest;
    }

    /**
     * @return Default {@link PaymentAuth}.
     */
    public static PaymentAuth getDefaultPaymentAuth() throws ZappModelValidationException {
        return new PaymentAuth(CheckoutType.NORMAL, DeliveryType.ADDRESS, getDefaultBankAccount(), getDefaultUser(), getDefaultCurrencyAmount(),
                getDefaultDeliveryAddress(), TransactionStatus.IN_PROGRESS, false);
    }

    /**
     * @return Default invalid {@link PaymentAuth}.
     */
    private static PaymentAuth getDefaultInvalidPaymentAuth() throws ZappModelValidationException {
        final PaymentAuth paymentAuth = new PaymentAuth(CheckoutType.NORMAL, DeliveryType.ADDRESS, getDefaultBankAccount(), getDefaultUser(), getDefaultCurrencyAmount(),
                getDefaultDeliveryAddress(), TransactionStatus.IN_PROGRESS, false);
        paymentAuth.setCheckoutType(null);
        return paymentAuth;
    }

    /**
     * @return Default {@link Merchant}.
     */
    public static Merchant getDefaultMerchant() throws ZappModelValidationException {
        return new Merchant("merchantId", "YourElectric", "office@yourelectric.co.uk", "", getDefaultAddress(), "YourElectric.com", /* logoUrl */ null);
    }

    /**
     * @return Default invalid {@link Merchant}.
     */
    private static Merchant getDefaultInvalidMerchant() throws ZappModelValidationException {
        final Merchant merchant = getDefaultMerchant();
        merchant.setIdentifier(null);
        return merchant;
    }

    /**
     * @return Default {@link BillDetails}.
     */
    private static BillDetails getDefaultBillDetails() throws ZappModelValidationException {
        return new BillDetails("accountId", "referenceId", new Date(0), new Date(0));
    }

    /**
     * @return Default invalid {@link BillDetails}.
     */
    private static BillDetails getDefaultInvalidBillDetails() throws ZappModelValidationException {
        final BillDetails billDetails = getDefaultBillDetails();
        billDetails.setAccountId(null);
        return billDetails;
    }

    /**
     * @return Default {@link User}.
     */
    public static User getDefaultUser() throws ZappModelValidationException {
        return new User("John", "Smith", "", "Mr.", "john.smith@mail.com", "");
    }

    /**
     * @return Default invalid {@link User}.
     */
    private static User getDefaultInvalidUser() throws ZappModelValidationException {
        final User user = getDefaultUser();
        user.setFirstName(null);
        user.setEmail(null);
        return user;
    }

    /**
     * @return Default {@link DeliveryAddress}.
     */
    public static DeliveryAddress getDefaultDeliveryAddress() throws ZappModelValidationException {
        final Address address = getDefaultAddress();
        return new DeliveryAddress("addressId", DeliveryAddressType.CFI, null, address.getLine1(), address.getLine2(), address.getLine3(), address.getLine4(),
                address.getLine5(),
                address.getLine6(), address.getPostCode(), address.getCountryCode());
    }

    /**
     * @return Default invalid {@link DeliveryAddress}.
     */
    private static DeliveryAddress getDefaultInvalidDeliveryAddress() throws ZappModelValidationException {
        final DeliveryAddress deliveryAddress = getDefaultDeliveryAddress();
        deliveryAddress.setType(null);
        return deliveryAddress;
    }

    /**
     * @return Default {@link CurrencyAmount}.
     */
    public static CurrencyAmount getDefaultCurrencyAmount() throws ZappModelValidationException {
        return new CurrencyAmount(3l, "GBP");
    }

    /**
     * @return Default invalid {@link CurrencyAmount}.
     */
    private static CurrencyAmount getDefaultInvalidCurrencyAmount() throws ZappModelValidationException {
        final CurrencyAmount currencyAmount = getDefaultCurrencyAmount();
        currencyAmount.setCurrencyCode(null);
        return currencyAmount;
    }

    /**
     * @return Default {@link BankAccount}.
     */
    public static BankAccount getDefaultBankAccount() throws ZappModelValidationException {
        return new BankAccount("0048", "accountName", "sortCode", getDefaultCurrencyAmount(), getDefaultCurrencyAmount());
    }

    /**
     * @return Default invalid {@link BankAccount}.
     */
    private static BankAccount getDefaultInvalidBankAccount() throws ZappModelValidationException {
        final BankAccount bankAccount = getDefaultBankAccount();
        bankAccount.setNumber(null);
        return bankAccount;
    }

    /**
     * @return Default {@link Address}.
     */
    public static Address getDefaultAddress() throws ZappModelValidationException {
        return new Address("221b Baker Street", "", "", "", "", "", "", "UK");
    }

    /**
     * @return Default invalid {@link Address}.
     */
    private static Address getDefaultInvalidAddress() throws ZappModelValidationException {
        final Address address = getDefaultAddress();
        address.setLine1(null);
        return address;
    }

    /**
     * @return Default {@link AmountDetail}.
     */
    public static AmountDetail getDefaultAmountDetails() throws ZappModelValidationException {
        return new AmountDetail(AmountType.CSHB, null, "1.10", "1.10");
    }

    /**
     * Adds a new default type and value to the map.
     *
     * @param map   The map which holds all default values.
     * @param type  The class which represents the object type.
     * @param value The default value for the given type.
     * @param <T>   The object type.
     */
    private static <T> void put(Map<Class<?>, Object> map, Class<T> type, T value) {
        map.put(type, value);
    }

    /**
     * Returns the default value of {@code type} as defined by JLS --- {@code 0} for numbers, {@code
     * false} for {@code boolean} and {@code '\0'} for {@code char}. For non-primitive types and
     * {@code void}, null is returned.
     */
    @SuppressWarnings("unchecked")
    public static <T> T defaultValue(Class<T> type) {
        return (T) DEFAULTS.get(type);
    }

    /**
     * Returns the default invalid value of {@code type}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T defaultInvalidValue(Class<T> type) {
        return (T) DEFAULT_INVALIDS.get(type);
    }
}