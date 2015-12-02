package uk.co.zapp.samplezappmerchantapp.configuration;

import com.zapp.core.Address;
import com.zapp.core.PaymentRequest;

import java.io.Serializable;

/**
 * Data access object for features in feature list.
 * @author msagi
 *
 * @see Features
 */
public class Feature implements Serializable {

    private Integer mId;

    private String mTitle;

    private String mDescription;

    private PaymentRequest mPaymentRequest;

    private Address customerAddress;

    private Address merchantStoreAddress;

    public Integer getId() {
        return mId;
    }

    public void setId(final Integer id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(final String description) {
        mDescription = description;
    }

    public PaymentRequest getPaymentRequest() {
        return mPaymentRequest;
    }

    public void setPaymentRequest(final PaymentRequest paymentRequest) {
        mPaymentRequest = paymentRequest;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(final Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Address getMerchantStoreAddress() {
        return merchantStoreAddress;
    }

    public void setMerchantStoreAddress(final Address merchantStoreAddress) {
        this.merchantStoreAddress = merchantStoreAddress;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Feature) {
            return mId == ((Feature) o).getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mId;
    }
}
