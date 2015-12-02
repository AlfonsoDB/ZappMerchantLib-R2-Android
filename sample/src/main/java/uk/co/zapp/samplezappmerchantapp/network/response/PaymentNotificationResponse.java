package uk.co.zapp.samplezappmerchantapp.network.response;

import com.zapp.core.DeliveryAddress;
import com.zapp.core.TransactionStatus;

/**
 * Payment notification response data object.
 *
 * Note: field names do not match coding standards. This is a trade off to be able to easily convert this data object to network request body using Gson.
 */
public class PaymentNotificationResponse extends BaseServerResponse {

    /**
     * The email.
     */
    private String email;

    /**
     * The phone number.
     */
    private String mobile;
    /**
     * The address details.
     */
    private DeliveryAddress addressDetails;

    /**
     * Flag for initiator is authorizer.
     */
    private boolean initiatorIsAuthorizer;

    /**
     * The transaction status.
     */
    private TransactionStatus transactionStatus;


    /**
     * Get the email.
     *
     * @return The {@link java.lang.String} email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the address details.
     *
     * @return The {@link com.zapp.core.Address} address details.
     */
    public DeliveryAddress getAddressDetails() {
        return addressDetails;
    }

    /**
     * Check if initiator is authorizer.
     *
     * @return True if initiator is authorizer, false otherwise.
     */
    public boolean isInitiatorIsAuthorizer() {
        return initiatorIsAuthorizer;
    }

    /**
     * Get payment status.
     *
     * @return The transaction status.
     */
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setAddressDetails(final DeliveryAddress addressDetails) {
        this.addressDetails = addressDetails;
    }

    public void setInitiatorIsAuthorizer(final boolean initiatorIsAuthorizer) {
        this.initiatorIsAuthorizer = initiatorIsAuthorizer;
    }

    public void setTransactionStatus(final TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
}
