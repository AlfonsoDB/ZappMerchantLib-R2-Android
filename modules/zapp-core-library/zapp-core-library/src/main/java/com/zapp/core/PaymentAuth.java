package com.zapp.core;

/**
 * The details for a payment authorisation.
 */
public class PaymentAuth implements IValidatable {

    /**
     * Represents if it is a Normal(Standard) or quick payment.
     */
    private CheckoutType checkoutType;

    /**
     * The payment delivery type.
     */
    private DeliveryType deliveryType;

    /**
     * The consumers bank account on which the transactions should be made.
     */
    private BankAccount bankAccount;

    /**
     * The consumer which makes the payment.
     */
    private User user;

    /**
     * The currency amount.
     */
    private CurrencyAmount finalAmount;

    /**
     * The delivery address.
     */
    private DeliveryAddress deliveryAddress;

    /**
     * The payment transactionStatus for a specific point in time.
     */
    private TransactionStatus transactionStatus;

    /**
     * Notify user via payconnect.
     */
    private Boolean payconnectEnabled;

    /**
     * The default constructor.
     */
    public PaymentAuth(final CheckoutType checkoutType, final DeliveryType deliveryType, final BankAccount bankAccount, final User user, final CurrencyAmount
            finalAmount, final DeliveryAddress deliveryAddress, final TransactionStatus transactionStatus, final Boolean payconnectEnabled) throws ZappModelValidationException {
        this.checkoutType = checkoutType;
        this.deliveryType = deliveryType;
        this.bankAccount = bankAccount;
        this.user = user;
        this.finalAmount = finalAmount;
        this.deliveryAddress = deliveryAddress;
        this.transactionStatus = transactionStatus;
        this.payconnectEnabled = payconnectEnabled;
        validate();
    }

    public CheckoutType getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(final CheckoutType checkoutType) {
        this.checkoutType = checkoutType;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(final DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(final BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public CurrencyAmount getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(final CurrencyAmount finalAmount) {
        this.finalAmount = finalAmount;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(final TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Boolean isPayconnectEnabled() {
        return payconnectEnabled;
    }

    public void setPayconnectEnabled(final Boolean payconnectEnabled) {
        this.payconnectEnabled = payconnectEnabled;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(transactionStatus, "PaymentAuth.transactionStatus");

        if (!ValidationUtils.isIn(transactionStatus, new TransactionStatus[]{TransactionStatus.DECLINED, TransactionStatus.PAYMENT_ENQUIRY_FAILED})) {
            ValidationUtils.require(checkoutType, "PaymentAuth.checkoutType");
            ValidationUtils.require(deliveryType, "PaymentAuth.deliveryType");

            if (checkoutType == CheckoutType.QUICK && deliveryType == DeliveryType.ADDRESS) {
                    ValidationUtils.require(deliveryAddress, "PaymentAuth.deliveryAddress");
            }
        }

        if (finalAmount != null) {
            finalAmount.validate();
        }

        if (bankAccount != null) {
            bankAccount.validate();
        }

        if (user != null) {
            user.validate();
        }

        if (deliveryAddress != null) {
            deliveryAddress.validate();
        }
    }
}
