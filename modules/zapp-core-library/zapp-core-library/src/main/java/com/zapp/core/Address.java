package com.zapp.core;

/**
 * Represents the user address.
 */
public class Address implements IValidatable {

    /**
     * The default country code for UK.
     */
    public static final String UK = "GBR";

    private String line1;

    private String line2;

    private String line3;

    private String line4;

    private String line5;

    private String line6;

    /**
     * The post code.
     */
    private String postCode;

    /**
     * The country code.
     */
    private String countryCode;

    /**
     * The default constructor which accepts all fields.
     */
    public Address(final String line1, final String line2, final String line3, final String line4, final String line5, final String line6, final String postCode,
            final String countryCode) throws ZappModelValidationException {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.line5 = line5;
        this.line6 = line6;
        this.postCode = postCode;
        this.countryCode = countryCode == null ? UK : countryCode;
        validate();
    }

    /**
     * The empty constructor. Use this constructor in child classes to avoid conflicting validate() methods during instance construction.
     */
    protected Address() {
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(final String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(final String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(final String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(final String line4) {
        this.line4 = line4;
    }

    public String getLine5() {
        return line5;
    }

    public void setLine5(final String line5) {
        this.line5 = line5;
    }

    public String getLine6() {
        return line6;
    }

    public void setLine6(final String line6) {
        this.line6 = line6;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(final String postCode) {
        this.postCode = postCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public void validate() throws ZappModelValidationException {
        ValidationUtils.require(line1, "Address.line1");
        ValidationUtils.require(countryCode, "Address.countryCode");
    }
}
