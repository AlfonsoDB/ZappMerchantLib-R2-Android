package com.zapp.core;

import java.util.Date;

/**
 * Data required in order to make a payment request.
 */
public class PaymentRequest implements IValidatable {

    /**
     * The payment option.
     */
    private RTPType rtpType;

    /**
     * The payment type.
     */
    private PaymentType paymentType;

    /**
     * Indicates whether it is a Normal(Standard) or quick payment.
     */
    private CheckoutType checkoutType;

    /**
     * The delivery type.
     */
    private DeliveryType deliveryType;

    /**
     * The transaction {@link MessageType} message type.
     */
    private MessageType messageType;

    /**
     * The consumer.
     */
    private User user;

    /**
     * The bill payment details.
     */
    private BillDetails billDetails;

    /**
     * The currency amount.
     */
    private CurrencyAmount amount;

    /**
     * The merchant.
     */
    private Merchant merchant;

    /**
     * The address sent by the user.
     */
    private Address address;

    /**
     * The callback url.
     */
    private String merchantCallbackUrl;

    /**
     * Authentication check required type.
     */
    private ACRType acrType;

    /**
     * Deferred Request To Pay Expiry Datetime (format: "YYYY-MM-DD").
     */
    private Date defrdRTPExpDateTime;

    /**
     * Deferred Request To Pay Agreement Amount.
     */
    private CurrencyAmount defrdRTPAgrmtAmount;

    /**
     * Deferred Request To Pay Maximum Agreed Amount.
     */
    private CurrencyAmount defrdRTPMaxAgrdAmount;

    /**
     * Deferred amount details.
     */
    private AmountDetail[] defrdAmountDetails;

    public PaymentRequest(final RTPType rtpType, final PaymentType paymentType, final CheckoutType checkoutType, final DeliveryType deliveryType, final User user,
            final BillDetails billDetails, final CurrencyAmount amount, final Merchant merchant, final Address address, final String merchantCallbackUrl,
            final ACRType acrType, final Date defrdRTPExpDateTime, final CurrencyAmount defrdRTPAgrmtAmount, final CurrencyAmount defrdRTPMaxAgrdAmount,
            final AmountDetail[] defrdAmountDetails) throws ZappModelValidationException {
        this.rtpType = rtpType;
        this.paymentType = paymentType;
        this.messageType = paymentType == PaymentType.SMB ? MessageType.BRN : MessageType.GENERAL;
        this.checkoutType = checkoutType;
        this.deliveryType = deliveryType;
        this.user = user;
        this.billDetails = billDetails;
        this.amount = amount;
        this.merchant = merchant;
        this.address = address;
        this.merchantCallbackUrl = merchantCallbackUrl;
        this.acrType = acrType;
        this.defrdRTPExpDateTime = defrdRTPExpDateTime;
        this.defrdRTPAgrmtAmount = defrdRTPAgrmtAmount;
        this.defrdRTPMaxAgrdAmount = defrdRTPMaxAgrdAmount;
        this.defrdAmountDetails = defrdAmountDetails;
        validate();
    }

    public RTPType getRtpType() {
        return rtpType;
    }

    public void setRtpType(final RTPType rtpType) {
        this.rtpType = rtpType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(final PaymentType paymentType) {
        this.paymentType = paymentType;
        this.messageType = paymentType == PaymentType.SMB ? MessageType.BRN : messageType;
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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public BillDetails getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(final BillDetails billDetails) {
        this.billDetails = billDetails;
    }

    public CurrencyAmount getAmount() {
        return amount;
    }

    public void setAmount(final CurrencyAmount amount) {
        this.amount = amount;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(final Merchant merchant) {
        this.merchant = merchant;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(final MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMerchantCallbackUrl() {
        return merchantCallbackUrl;
    }

    public void setMerchantCallbackUrl(final String merchantCallbackUrl) {
        this.merchantCallbackUrl = merchantCallbackUrl;
    }

    public ACRType getAcrType() {
        return acrType;
    }

    public void setAcrType(final ACRType acrType) {
        this.acrType = acrType;
    }

    public AmountDetail[] getDefrdAmountDetails() {
        return defrdAmountDetails;
    }

    public void setDefrdAmountDetails(final AmountDetail[] defrdAmountDetails) {
        this.defrdAmountDetails = defrdAmountDetails;
    }

    public CurrencyAmount getDefrdRTPAgrmtAmount() {
        return defrdRTPAgrmtAmount;
    }

    public void setDefrdRTPAgrmtAmount(final CurrencyAmount defrdRTPAgrmtAmount) {
        this.defrdRTPAgrmtAmount = defrdRTPAgrmtAmount;
    }

    public Date getDefrdRTPExpDateTime() {
        return defrdRTPExpDateTime;
    }

    public void setDefrdRTPExpDateTime(final Date defrdRTPExpDateTime) {
        this.defrdRTPExpDateTime = defrdRTPExpDateTime;
    }

    public CurrencyAmount getDefrdRTPMaxAgrdAmount() {
        return defrdRTPMaxAgrdAmount;
    }

    public void setDefrdRTPMaxAgrdAmount(final CurrencyAmount defrdRTPMaxAgrdAmount) {
        this.defrdRTPMaxAgrdAmount = defrdRTPMaxAgrdAmount;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(rtpType, "PaymentRequest.rtpType");
        ValidationUtils.require(deliveryType, "PaymentRequest.deliveryType");
        ValidationUtils.require(messageType, "PaymentRequest.messageType");
        ValidationUtils.require(merchant, "PaymentRequest.merchant");
        merchant.validate();
        ValidationUtils.require(paymentType, "PaymentRequest.paymentType");
        ValidationUtils.require(checkoutType, "PaymentRequest.checkoutType");

        if (rtpType == RTPType.IMMEDIATE) {
            ValidationUtils.require(amount, "PaymentRequest.amount");
            amount.validate();
            ValidationUtils.require(amount.getValue() > 0, "PaymentRequest.amount.value must be positive");
            ValidationUtils.requireNull(acrType, "PaymentRequest.acrType");
            ValidationUtils.requireNull(defrdRTPExpDateTime, "PaymentRequest.defrdRTPExpDateTime");
            ValidationUtils.requireNull(defrdRTPAgrmtAmount, "PaymentRequest.defrdRTPAgrmtAmount");
            ValidationUtils.requireNull(defrdRTPMaxAgrdAmount, "PaymentRequest.defrdRTPMaxAgrdAmount");
            ValidationUtils.requireNull(defrdAmountDetails, "PaymentRequest.defrdAmountDetails");
        } else if (rtpType == RTPType.DEFERRED) {
            ValidationUtils.requireNull(amount, "PaymentRequest.amount");
            ValidationUtils.require(acrType, "PaymentRequest.acrType");
            ValidationUtils.require(defrdRTPAgrmtAmount, "PaymentRequest.defrdRTPAgrmtAmount");
            defrdRTPAgrmtAmount.validate();
            ValidationUtils.require(defrdRTPAgrmtAmount.getValue() > 0, "PaymentRequest.defrdRTPAgrmtAmount.value must be positive");
            if (defrdRTPMaxAgrdAmount != null) {
                defrdRTPMaxAgrdAmount.validate();
            }
            if (defrdAmountDetails != null) {
                for (final AmountDetail amountDetail : defrdAmountDetails) {
                    amountDetail.validate();
                }
            }
        }

        ValidationUtils.requireCombinationIn(new Object[]{rtpType, paymentType, checkoutType},
                new Object[]{RTPType.IMMEDIATE, PaymentType.INSTANT_PAYMENT, CheckoutType.NORMAL},
                new Object[]{RTPType.IMMEDIATE, PaymentType.INSTANT_PAYMENT, CheckoutType.QUICK},
                new Object[]{RTPType.IMMEDIATE, PaymentType.BILL_PAY, CheckoutType.NORMAL},
                new Object[]{RTPType.IMMEDIATE, PaymentType.SMB, CheckoutType.NORMAL},

                new Object[]{RTPType.DEFERRED, PaymentType.INSTANT_PAYMENT, CheckoutType.NORMAL},
                new Object[]{RTPType.DEFERRED, PaymentType.INSTANT_PAYMENT, CheckoutType.QUICK}
        );

        if (paymentType == PaymentType.BILL_PAY) {
            ValidationUtils.require(billDetails, "PaymentRequest.billDetails");
            billDetails.validate();
        } else {
            ValidationUtils.requireNull(billDetails, "PaymentRequest.billDetails must be null if paymentType is not BILL_PAY");
        }

        if (checkoutType == CheckoutType.QUICK) {
            ValidationUtils.requireNull(address, "PaymentRequest.address must be null if checkoutType is QUICK");
            ValidationUtils.requireIn(deliveryType, new DeliveryType[]{DeliveryType.ADDRESS, DeliveryType.DIGITAL},
                    "PaymentRequest.deliveryType must be ADDRESS or DIGITAL if checkoutType is QUICK");
        }
        if (user != null) {
            user.validate();
        }
        if (address != null) {
            address.validate();
        }
    }
}
