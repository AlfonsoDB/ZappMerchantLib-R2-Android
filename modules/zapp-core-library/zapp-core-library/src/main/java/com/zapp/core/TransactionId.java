package com.zapp.core;

/**
 * The payment transaction unique id.
 */
public class TransactionId implements IValidatable {

    /**
     * Alternative payment transaction id.
     */
    private String aptId;

    /**
     * Human friendly transaction retrieval identifier issued by Zapp, preferred method for retrieving / identifying transactions after completion.
     */
    private String aptrId;

    /**
     * Creates a new TransactionId.
     *
     * @param aptId  The aptId.
     * @param aptrId The aptrId.
     */
    public TransactionId(final String aptId, final String aptrId) throws ZappModelValidationException {
        this.aptId = aptId;
        this.aptrId = aptrId;
        validate();
    }

    public String getAptId() {
        return aptId;
    }

    public void setAptId(final String aptId) {
        this.aptId = aptId;
    }

    public String getAptrId() {
        return aptrId;
    }

    public void setAptrId(final String aptrId) {
        this.aptrId = aptrId;
    }

    /**
     * Two transaction ids are considered to be equals when their aptId and aptrId are equal.
     *
     * @param obj A new TransactionId to compare with.
     * @return true if aptId and aptrId are equals.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof TransactionId &&
                aptrId != null &&
                aptrId.equals(((TransactionId) obj).aptrId) &&
                aptId != null &&
                aptId.equals(((TransactionId) obj).aptId);
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(aptId, "TransactionId.aptId");
        ValidationUtils.require(aptrId, "TransactionId.aptrId");
    }
}
