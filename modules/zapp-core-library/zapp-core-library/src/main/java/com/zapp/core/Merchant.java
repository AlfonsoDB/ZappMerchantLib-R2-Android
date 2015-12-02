package com.zapp.core;

/**
 * Represents details specific to merchant.
 */
public class Merchant implements IValidatable {

    /**
     * The merchant's unique identifier.
     */
    private String identifier;

    /**
     * The merchant's name.
     */
    private String name;

    /**
     * The merchant's email.
     */
    private String email;

    /**
     * The merchant's contact phone.
     */
    private String phone;

    /**
     * The merchant's address.
     */
    private Address address;

    /**
     * The merchant's website.
     */
    private String website;

    /**
     * The merchant's logoUrl.
     */
    private String logoUrl;

    /**
     * The default constructor.
     */
    public Merchant(final String identifier, final String name, final String email, final String phone, final Address address, final String website, final String logoUrl)
            throws ZappModelValidationException {
        this.identifier = identifier;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.website = website;
        this.logoUrl = logoUrl;
        validate();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(final String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(identifier, "Merchant.identifier");
        if (address != null) {
            address.validate();
        }
        if (email != null) {
            ValidationUtils.requireValidEmail(email, "Merchant.email is not valid");
        }
    }
}
