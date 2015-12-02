package com.zapp.core;

/**
 * The consumer's address.
 */
public class DeliveryAddress extends Address {

    /**
     * Unique address identifier.
     */
    private String identifier;

    /**
     * The delivery address type should default to {@link com.zapp.core.DeliveryAddressType#GENERAL}
     */
    private DeliveryAddressType type = DeliveryAddressType.GENERAL;

    /**
     * The user which this address refers to.
     */
    private User addressee;

    /**
     * The default constructor.
     */
    public DeliveryAddress(final String identifier, final DeliveryAddressType type, final User addressee, final String line1, final String line2, final String line3,
            final String line4, final String line5, final String line6, final String postCode, final String countryCode) throws ZappModelValidationException {
        super();
        setLine1(line1);
        setLine2(line2);
        setLine3(line3);
        setLine4(line4);
        setLine5(line5);
        setLine6(line6);
        setPostCode(postCode);
        setCountryCode(countryCode);
        this.identifier = identifier;
        this.type = type;
        this.addressee = addressee;
        validate();
    }

    public DeliveryAddressType getType() {
        return type;
    }

    public void setType(final DeliveryAddressType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public User getAddressee() {
        return addressee;
    }

    public void setAddressee(final User addressee) {
        this.addressee = addressee;
    }

    /**
     * Two delivery addresses will be equal if their identifiers will be equal.
     *
     * @param object A new delivery address which should be compared against.
     * @return true if the address identifiers are equal.
     */
    @Override
    public boolean equals(final Object object) {
        return object instanceof DeliveryAddress &&
                ((DeliveryAddress) object).identifier != null &&
                ((DeliveryAddress) object).identifier.equals(identifier);
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        super.validate();
        ValidationUtils.require(type, "DeliveryAddress.type");
        if (addressee != null) {
            addressee.validate();
        }
    }
}
