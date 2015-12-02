package com.zapp.core;

/**
 * Amount detail model object.
 *
 * @author msagi
 */
public class AmountDetail implements IValidatable {

    /**
     * The amount type.
     */
    private AmountType type;

    /**
     * The amount description.
     */
    private String description;

    /**
     * The price.
     */
    private String price;

    /**
     * The rate.
     */
    private String rate;

    public AmountDetail(final AmountType type, final String description, final String price, final String rate) throws ZappModelValidationException {
        this.type = type;
        this.description = description;
        this.price = price;
        this.rate = rate;
        validate();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(final String rate) {
        this.rate = rate;
    }

    public AmountType getType() {
        return type;
    }

    public void setType(final AmountType type) {
        this.type = type;
    }

    @Override
    public void validate() throws ZappModelValidationException {
        ValidationUtils.require(type, "AmountDetail.type");
        ValidationUtils.require(price, "AmountDetail.price");
        ValidationUtils.requireValidAmount_14_2(price, "AmountDetail.price");
        if (rate != null) {
            ValidationUtils.requireValidAmount_14_2(rate, "AmountDetail.rate");
        }
    }
}
