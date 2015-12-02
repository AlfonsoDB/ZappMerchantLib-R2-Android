package com.zapp.core;

import java.util.Date;

/**
 * Bill payment details.
 */
public class BillDetails implements IValidatable {

    /**
     * Bill Payment Consumer Account Identifier.
     */
    private String accountId;

    /**
     * Biller / Bill Reference.
     */
    private String reference;

    /**
     * Bill Pay Billing Period From [yyyy-MM-dd].
     */
    private Date periodFrom;

    /**
     * Bill Pay Billing Period To [yyyy-MM-dd].
     */
    private Date periodTo;

    /**
     * The default constructor.
     */
    public BillDetails(final String accountId, final String reference, final Date periodFrom, final Date periodTo) throws ZappModelValidationException {
        this.accountId = accountId;
        this.reference = reference;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        validate();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(final Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(final Date periodTo) {
        this.periodTo = periodTo;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(reference, "BillDetails.reference");

        if (periodFrom != null && periodTo != null) {
            ValidationUtils.require(periodFrom.compareTo(periodTo) < 1, "BillDetails.periodFrom cannot be after BillDetails.periodTo");
        }
    }
}
