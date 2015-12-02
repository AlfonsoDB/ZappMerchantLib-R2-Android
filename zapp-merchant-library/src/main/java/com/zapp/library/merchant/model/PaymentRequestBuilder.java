package com.zapp.library.merchant.model;

import com.zapp.core.ACRType;
import com.zapp.core.Address;
import com.zapp.core.AmountDetail;
import com.zapp.core.BillDetails;
import com.zapp.core.CheckoutType;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryType;
import com.zapp.core.Merchant;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.User;
import com.zapp.core.ZappModelValidationException;

import java.util.Date;

/**
 * A builder for {@link com.zapp.core.PaymentRequest} model.
 */
public class PaymentRequestBuilder {

    /**
     * The payment option.
     */
    private RTPType mRtpType;

    /**
     * The payment type.
     */
    private PaymentType mPaymentType;

    /**
     * Indicates whether it is a Normal(Standard) or quick payment.
     */
    private CheckoutType mCheckoutType;

    /**
     * The delivery type.
     */
    private DeliveryType mDeliveryType;

    /**
     * The consumer.
     */
    private User mUser;

    /**
     * The bill payment details.
     */
    private BillDetails mBillDetails;

    /**
     * The currency amount.
     */
    private CurrencyAmount mAmount;

    /**
     * The merchant.
     */
    private Merchant mMerchant;

    /**
     * The address sent by the user.
     */
    private Address mAddress;

    /**
     * The merchant's callback url.
     */
    private String mMerchantCallbackUrl;

    /**
     * Authentication check required type.
     */
    private ACRType mAcrType;

    /**
     * Deferred Request To Pay Expiry Date (format: "YYYY-MM-DD").
     */
    private Date mDefrdRTPExpDateTime;

    /**
     * Deferred Request To Pay Agreement Amount.
     */
    private CurrencyAmount mDefrdRTPAgrmtAmount;

    /**
     * Deferred Request To Pay Maximum Agreed Amount.
     */
    private CurrencyAmount mDefrdRTPMaxAgrdAmount;

    /**
     * Deferred amount details.
     */
    private AmountDetail[] mDefrdAmountDetails;

    public PaymentRequestBuilder withRtpType(final RTPType rtpType) {
        mRtpType = rtpType;
        return this;
    }

    public PaymentRequestBuilder withAddress(final Address address) {
        mAddress = address;
        return this;
    }

    public PaymentRequestBuilder withAmount(final CurrencyAmount amount) {
        mAmount = amount;
        return this;
    }

    public PaymentRequestBuilder withBillDetails(final BillDetails billDetails) {
        mBillDetails = billDetails;
        return this;
    }

    public PaymentRequestBuilder withCheckoutType(final CheckoutType checkoutType) {
        mCheckoutType = checkoutType;
        return this;
    }

    public PaymentRequestBuilder withDeliveryType(final DeliveryType deliveryType) {
        mDeliveryType = deliveryType;
        return this;
    }

    public PaymentRequestBuilder withMerchant(final Merchant merchant) {
        mMerchant = merchant;
        return this;
    }

    public PaymentRequestBuilder withPaymentType(final PaymentType paymentType) {
        mPaymentType = paymentType;
        return this;
    }

    public PaymentRequestBuilder withUser(final User user) {
        mUser = user;
        return this;
    }

    public PaymentRequestBuilder withMerchantCallbackUrl(final String merchantCallbackUrl) {
        mMerchantCallbackUrl = merchantCallbackUrl;
        return this;
    }

    public PaymentRequestBuilder withAcrType(final ACRType acrType) {
        mAcrType = acrType;
        return this;
    }

    public PaymentRequestBuilder withDefrdRTPExpDateTime(final Date defrdRTPExpDateTime) {
        mDefrdRTPExpDateTime = defrdRTPExpDateTime;
        return this;
    }

    public PaymentRequestBuilder withDefrdRTPAgrmtAmount(final CurrencyAmount defrdRTPAgrmtAmount) {
        mDefrdRTPAgrmtAmount = defrdRTPAgrmtAmount;
        return this;
    }

    public PaymentRequestBuilder withDefrdRTPMaxAgrdAmount(final CurrencyAmount defrdRTPMaxAgrdAmount) {
        mDefrdRTPMaxAgrdAmount = defrdRTPMaxAgrdAmount;
        return this;
    }

    public PaymentRequestBuilder withDefrdAmountDetails(final AmountDetail[] defrdAmountDetails) {
        mDefrdAmountDetails = defrdAmountDetails;
        return this;
    }

    /**
     * Build the payment request instance.
     *
     * @return The {@link PaymentRequest} built from the current state of the builder.
     * @throws IllegalStateException If the state of the builder is invalid so it cannot make valid payment request out of its current state.
     */
    public PaymentRequest build() throws IllegalStateException {
        try {
            return new PaymentRequest(mRtpType, mPaymentType, mCheckoutType, mDeliveryType, mUser, mBillDetails, mAmount, mMerchant, mAddress, mMerchantCallbackUrl,
                    mAcrType, mDefrdRTPExpDateTime, mDefrdRTPAgrmtAmount, mDefrdRTPMaxAgrdAmount, mDefrdAmountDetails);
        } catch (ZappModelValidationException zmve) {
            throw new IllegalStateException("Failed to build payment request", zmve);
        }
    }
}
