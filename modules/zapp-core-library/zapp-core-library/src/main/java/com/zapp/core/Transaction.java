package com.zapp.core;

/**
 * Holds details for a payment transaction.
 */
public class Transaction implements IValidatable {

    /**
     * The 6 character Zapp Code / Basket Reference Number for entry in the mobile banking app on the consumer’s device.
     */
    private String brn;

    /**
     * The payment transaction id.
     */
    private TransactionId transactionId;

    /**
     * The payment request details.
     */
    private PaymentRequest paymentRequest;

    /**
     * The payment authorisation details.
     */
    private PaymentAuth paymentAuth;

    /**
     * The time interval defined by the zapp core in order to confirm the payment.
     */
    private Integer confirmationExpiryInterval;

    /**
     * The time interval defined by the zapp core in order to retrieve the payment details.
     */
    private Integer retrievalExpiryInterval;

    /**
     * The payment authorisation details retrieval method.
     */
    private RetrievalMethod retrievalMethod;

    /**
     * Represents the custom scheme used by an android intent in order to open the merchant app after the bank app finishes to process the transaction.
     */
    private String merchantCallbackUrl;

    /**
     * Is used to know when the consumer should be asked for payconnect.
     */
    private Boolean askForPayconnect;

    /**
     * Is used to know if the device uses payconnect.
     */
    private Boolean initiatorIsAuthorizer;

    /**
     * The identifier that is obtained when the payment is finished.
     */
    private String settlementRetrievalId;

    /**
     * Indicates if a payment notification was sent.
     */
    private Boolean notificationSent;

    /**
     * The default constructor.
     */
    public Transaction(final String brn, final TransactionId transactionId, final PaymentRequest paymentRequest, final PaymentAuth paymentAuth,
            final Integer retrievalExpiryInterval, final Integer confirmationExpiryInterval, final RetrievalMethod retrievalMethod, final String merchantCallbackUrl,
            final Boolean askForPayconnect, final Boolean initiatorIsAuthorizer, final String settlementRetrievalId, final Boolean notificationSent)
            throws ZappModelValidationException {
        this.brn = brn;
        this.transactionId = transactionId;
        this.paymentRequest = paymentRequest;
        this.paymentAuth = paymentAuth;
        this.retrievalExpiryInterval = retrievalExpiryInterval;
        this.confirmationExpiryInterval = confirmationExpiryInterval;
        this.retrievalMethod = retrievalMethod;
        this.merchantCallbackUrl = merchantCallbackUrl;
        this.askForPayconnect = askForPayconnect;
        this.initiatorIsAuthorizer = initiatorIsAuthorizer;
        this.settlementRetrievalId = settlementRetrievalId;
        this.notificationSent = notificationSent;
        validate();
    }

    public String getBrn() {
        return brn;
    }

    public void setBrn(final String brn) {
        this.brn = brn;
    }

    public String getMerchantCallbackUrl() {
        return merchantCallbackUrl;
    }

    public void setMerchantCallbackUrl(final String merchantCallbackUrl) {
        this.merchantCallbackUrl = merchantCallbackUrl;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getTransactionStatus() {
        return paymentAuth == null ? TransactionStatus.IN_PROGRESS : paymentAuth.getTransactionStatus();
    }

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(final PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public PaymentAuth getPaymentAuth() {
        return paymentAuth;
    }

    public void setPaymentAuth(final PaymentAuth paymentAuth) {
        this.paymentAuth = paymentAuth;
    }

    public Integer getRetrievalExpiryInterval() {
        return retrievalExpiryInterval;
    }

    public void setRetrievalExpiryInterval(final Integer retrievalExpiryInterval) {
        this.retrievalExpiryInterval = retrievalExpiryInterval;
    }

    public Integer getConfirmationExpiryInterval() {
        return confirmationExpiryInterval;
    }

    public void setConfirmationExpiryInterval(final Integer confirmationExpiryInterval) {
        this.confirmationExpiryInterval = confirmationExpiryInterval;
    }

    public RetrievalMethod getRetrievalMethod() {
        return retrievalMethod;
    }

    public void setRetrievalMethod(final RetrievalMethod retrievalMethod) {
        this.retrievalMethod = retrievalMethod;
    }

    public Boolean isAskForPayconnect() {
        return askForPayconnect;
    }

    public void setAskForPayconnect(final Boolean askForPayconnect) {
        this.askForPayconnect = askForPayconnect;
    }

    public Boolean getInitiatorIsAuthorizer() {
        return initiatorIsAuthorizer;
    }

    public void setInitiatorIsAuthorizer(final Boolean initiatorIsAuthorizer) {
        this.initiatorIsAuthorizer = initiatorIsAuthorizer;
    }

    public String getSettlementRetrievalId() {
        return settlementRetrievalId;
    }

    public void setSettlementRetrievalId(final String settlementRetrievalId) {
        this.settlementRetrievalId = settlementRetrievalId;
    }

    public Boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(final Boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    /**
     * Two transactions are considered to be equal if their ids or brn codes are equal.
     * The bank apps core module adds Transactions to a pull where they are processed one by one. The equals method makes it possible to compare/find/remove them
     * quickly.
     *
     * @param toCompare A transaction to be compared with.
     * @return true if transactions ids or brn codes are equal.
     */
    @Override
    public boolean equals(final Object toCompare) {
        boolean equals = false;
        if (toCompare instanceof Transaction) {
            if (transactionId != null) {
                equals = transactionId.equals(((Transaction) toCompare).getTransactionId());
            } else {
                equals = brn != null && brn.equals(((Transaction) toCompare).getBrn());
            }
        }

        return equals;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(retrievalMethod, "Transaction.retrievalMethod");
        ValidationUtils.require(retrievalExpiryInterval, "Transaction.retrievalExpiryInterval");
        ValidationUtils.require(notificationSent, "Transaction.notificationSent");

        if (transactionId != null) {
            transactionId.validate();
        }
        if (paymentRequest != null) {
            paymentRequest.validate();

            if (paymentRequest.getRtpType() == RTPType.IMMEDIATE) {
                ValidationUtils.require(settlementRetrievalId, "Transaction.settlementRetrievalId");
            }
        }
        if (paymentAuth != null) {
            paymentAuth.validate();
        }
    }

    @Override
    public String toString() {
        final String aptId = transactionId == null ? null : transactionId.getAptId();
        return String.format("Transaction[brn:%s, aptId:%s]", brn, aptId);
    }
}
